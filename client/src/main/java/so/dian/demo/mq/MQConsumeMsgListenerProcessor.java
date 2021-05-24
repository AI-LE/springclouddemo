package so.dian.demo.mq;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sali
 * @date 2021/4/2 下午1:08
 * @Description
 */
@Component
@Log4j2
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
 
    @Value("${apache.rocketmq.consumer.topic}")
    private String topic;
    @Value("${apache.rocketmq.tag}")
    private String TAG_SOCKET_MSG;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        MessageExt messageExt = msgs.get(0);
        log.info("接受到的消息为：" + messageExt.toString());
        if (messageExt.getTopic().equals(topic)) {
            if (messageExt.getTags().equals(TAG_SOCKET_MSG)) {
                int reconsume = messageExt.getReconsumeTimes();
                if (reconsume == 3) {// 消息已经重试了3次，如果不需要再次消费，则返回成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //TODO  处理对应的业务逻辑
                log.info("接收的body体：" + new String(messageExt.getBody()));
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
 
    }

}