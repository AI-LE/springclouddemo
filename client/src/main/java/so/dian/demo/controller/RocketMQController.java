package so.dian.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import so.dian.demo.mq.MessageDto;
import so.dian.demo.mq.MqProducerServiceImpl;

import javax.annotation.Resource;

/**
 * @author sali
 * @date 2021/4/2 下午1:19
 * @Description
 */
@RestController
public class RocketMQController {

    @Resource
    private MqProducerServiceImpl mqProducerService;

    @RequestMapping("/mqSend")
    public String mqSend(MessageDto messageDto) throws Exception{
        return mqProducerService.sendSocketMsg(messageDto);
    }
}
