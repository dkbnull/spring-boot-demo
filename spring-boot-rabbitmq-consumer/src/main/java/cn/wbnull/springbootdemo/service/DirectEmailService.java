package cn.wbnull.springbootdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * DirectEmailService
 * <p>
 * bindings 用来确定队列和交换机的绑定关系
 * value    队列名称，与生产者对应
 * exchange 交换机名称，与生产者对应；type设置RabbitMQ模式为direct
 * key      路由key
 *
 * @author dukunbiao(null)  2024-04-08
 * https://github.com/dkbnull/SpringBootDemo
 */
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "email.direct.queue", durable = "true", autoDelete = "false"),
        exchange = @Exchange(value = "direct_user_exchange"),
        key = "email"))
@Service
@Slf4j
public class DirectEmailService {

    /**
     * 消息接收的方法
     *
     * @param message
     */
    @RabbitHandler
    public void receiveMessage(String message) {
        //发送邮件

        log.info("email.direct：{}", message);
    }
}
