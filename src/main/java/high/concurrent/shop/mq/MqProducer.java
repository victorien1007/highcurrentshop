package high.concurrent.shop.mq;

import com.alibaba.fastjson.JSON;
import high.concurrent.shop.error.BusinessException;
import high.concurrent.shop.service.OrderService;
import io.lettuce.core.TransactionResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class MqProducer {
private DefaultMQProducer producer;
private TransactionMQProducer transactionMQProducer;
@Value("${mq.nameserver.addr}")
private String nameAddr;

@Value("${mq.topicname}")
private String topicName;

@Autowired
private OrderService orderService;

@PostConstruct
    public void init() throws MQClientException {

    producer=new DefaultMQProducer("producer_group");
    producer.setNamesrvAddr(nameAddr);

    producer.start();

    transactionMQProducer=new TransactionMQProducer("transaction_producer_group");
    transactionMQProducer.setNamesrvAddr(nameAddr);
    transactionMQProducer.start();
    transactionMQProducer.setTransactionListener(new TransactionListener() {
        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object obj) {

            Integer productId=(Integer)((Map)obj).get("productId");
            Integer userId=(Integer)((Map)obj).get("userId");
            Integer promoId=(Integer)((Map)obj).get("promoId");
            Integer amount=(Integer)((Map)obj).get("amount");
            try {
                orderService.createOrder(userId,productId,promoId,amount);
            }catch(BusinessException e){
                e.printStackTrace();
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

            return LocalTransactionState.COMMIT_MESSAGE;
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            //根据是否扣减库存成功，来判断要返回commit,rollback还是继续unknown
            String jsonString=new String(messageExt.getBody());
            Map<String,Object> map = JSON.parseObject(jsonString,Map.class);
            Integer productId=(Integer) map.get("productId");
            Integer amount=(Integer) map.get("amount");
            return null;
        }
    });

}

    //事务型同步库存扣减消息
    public boolean transactionAsyncReduceStock(Integer userId, Integer productId,Integer promoId,  Integer amount){
        Map<String,Object> bodyMap=new HashMap<>();
        bodyMap.put("productId",productId);
        bodyMap.put("amount",amount);
        Map<String,Object> argsMap=new HashMap<>();
        argsMap.put("productId",productId);
        argsMap.put("amount",amount);
        argsMap.put("userId",userId);
        argsMap.put("promoId",promoId);
        Message message=new Message(topicName,"increase", JSON.toJSON(bodyMap).toString().getBytes(Charset.forName("UTF-8")));

        TransactionSendResult transactionSendResult=null;
        try {
            transactionSendResult = transactionMQProducer.sendMessageInTransaction(message,argsMap);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        if(transactionSendResult.getLocalTransactionState()==LocalTransactionState.ROLLBACK_MESSAGE)
            return false;
        else if(transactionSendResult.getLocalTransactionState()==LocalTransactionState.COMMIT_MESSAGE){
            return true;
        }
        return false;
    }
    //同步库存扣减消息
    public boolean asyncReduceStock(Integer productId, Integer amount){
        Map<String,Object> bodyMap=new HashMap<>();
        bodyMap.put("productId",productId);
        bodyMap.put("amount",amount);
        Message message=new Message(topicName,"increase", JSON.toJSON(bodyMap).toString().getBytes(Charset.forName("UTF-8")));
        try {
            producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        } catch (RemotingException e) {
            e.printStackTrace();
            return false;
        } catch (MQBrokerException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
