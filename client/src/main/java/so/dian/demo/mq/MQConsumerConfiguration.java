package so.dian.demo.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author sali
 * @date 2021/4/2 下午1:07
 * @Description
 */
@Configuration

public class MQConsumerConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerConfiguration.class);

    @Value("${apache.rocketmq.name-server}")

    private String namesrvAddr;

    @Value("${apache.rocketmq.producer.algorithm.comsumerGroup}")

    private String groupName;

    @Value("${apache.rocketmq.consumer.consumeThreadMin}")

    private int consumeThreadMin;

    @Value("${apache.rocketmq.consumer.consumeThreadMax}")

    private int consumeThreadMax;

    @Value("${apache.rocketmq.consumer.topic}")

    private String topic;

    @Value("${apache.rocketmq.consumer.tag}")

    private String tag;

    @Value("${apache.rocketmq.consumer.consumeMessageBatchMaxSize}")

    private int consumeMessageBatchMaxSize;

    @Autowired

    private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception {


        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);

        //set to broadcast mode

        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.setNamesrvAddr(namesrvAddr);

        consumer.setConsumeThreadMin(consumeThreadMin);

        consumer.setConsumeThreadMax(consumeThreadMax);

        consumer.registerMessageListener(mqMessageListenerProcessor);

        /**
          * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费 如果非第一次启动，那么按照上次消费的位置继续消费
          */

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        /**
          * 设置消费模型，集群还是广播，默认为集群
          */

        // consumer.setMessageModel(MessageModel.CLUSTERING);

        /**
          * 设置一次消费消息的条数，默认为1条
          */

        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        try {

            consumer.subscribe(topic, tag);

            consumer.start();

            LOGGER.info("consumer is start !!! groupName:{},topic:{},tag:{},namesrvAddr:{}", groupName, topic, tag, namesrvAddr);

        } catch (Exception e) {

            LOGGER.error("consumer is start !!! groupName:{},topic:{},tag:{},namesrvAddr:{}", groupName, topic, tag, namesrvAddr,

                    e);

            throw new Exception(e.getMessage());

        }

        return consumer;

    }

}