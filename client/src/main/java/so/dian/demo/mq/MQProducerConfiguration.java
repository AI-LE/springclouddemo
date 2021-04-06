package so.dian.demo.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * @author sali
 * @date 2021/4/2 下午12:44
 * @Description
 */
@Configuration
@Order(0)
public class MQProducerConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(MQProducerConfiguration.class);
    /**
     *  * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     * <p>
     *  
     */
    @Value("${apache.rocketmq.producer.algorithm.producerGroup}")
    private String groupName;
    @Value("${apache.rocketmq.name-server}")
    private String namesrvAddr;
    /**
     *  * 消息最大大小，默认4M
     *  
     */
    @Value("${apache.rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;

    /**
     *  * 消息发送超时时间，默认3秒
     *  
     */
    @Value("${apache.rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    /**
     *  * 消息发送失败重试次数，默认2次
     *  
     */
    @Value("${apache.rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Bean
    public DefaultMQProducer getRocketMQProducer() throws Exception {

        if (StringUtils.isEmpty(this.groupName)) {

            throw new Exception("groupName is blank");

        }

        if (StringUtils.isEmpty(this.namesrvAddr)) {

            throw new Exception("nameServerAddr is blank");

        }

        DefaultMQProducer producer;

        producer = new DefaultMQProducer(this.groupName);

        producer.setNamesrvAddr(this.namesrvAddr);

        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName

        //producer.setInstanceName(instanceName);

//  if(this.maxMessageSize!=null){

//   producer.setMaxMessageSize(this.maxMessageSize);

//  }

        if (this.sendMsgTimeout != null) {

            producer.setSendMsgTimeout(this.sendMsgTimeout);

        }

        //如果发送消息失败，设置重试次数，默认为2次

        if (this.retryTimesWhenSendFailed != null) {

            producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);

        }

        try {

//            producer.start();
            producer.setVipChannelEnabled(false);
            LOGGER.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"

                    , this.groupName, this.namesrvAddr));

        } catch (Exception e) {

            LOGGER.error(String.format("producer is error {}"

                    , e.getMessage(), e));

            throw new Exception(e.getMessage());

        }

        return producer;

    }

}
