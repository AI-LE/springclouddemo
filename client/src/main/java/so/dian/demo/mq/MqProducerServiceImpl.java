package so.dian.demo.mq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author sali
 * @date 2021/4/2 下午12:54
 * @Description
 */
@Component(value = "mqProducerService")

@Log4j2

//实现类

public class MqProducerServiceImpl {

    @Autowired
    private DefaultMQProducer producer;

    @Value("${apache.rocketmq.producer.topic}")
    private String topic;
    @Value("${apache.rocketmq.tag}")
    private String tag;

    public String sendSocketMsg(MessageDto obj){
        try {
            Message msg;
            msg = new Message(topic, tag, JSONObject.toJSONBytes(obj));
            msg.putUserProperty("request-id", UUID.randomUUID().toString().replace("-", ""));
//            log.info("MQ请求参数：" + JsonUtils.beanToJson(obj));
            SendResult sendResult = producer.send(msg);
            log.info("MQ结果返回：" + JSONObject.toJSONString(sendResult));
            return JSONObject.toJSONString(sendResult);
        } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return "失败了";
    }

}