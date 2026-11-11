package cn.wbnull.springbootdemo.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * MessageConfirmCallback
 *
 * @author dukunbiao(null)  2024-04-08
 * https://github.com/dkbnull/SpringBootDemo
 */
@Slf4j
public class MessageConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            log.info("消息确认成功，correlationData={}", correlationData);
        } else {
            log.error("消息确认失败，cause={}，correlationData={}", s, correlationData);
        }
    }
}
