package cn.wbnull.springbootdemo;

import cn.wbnull.springbootdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RabbitMqProducerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RabbitMqProducerApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        userService.register("张三", "zhangsan");
    }

    @Test
    public void contextLoadsDirect() {
        userService.registerDirect("李四", "lisi");
    }

    @Test
    public void contextLoadsTopic() {
        userService.registerTopic("王五", "wangwu");
    }

    @Test
    public void contextLoadsTopicCallback() {
        userService.registerTopicCallback("周六", "zhouliu");
    }

    @Test
    public void contextLoadsTopicDlx() {
        userService.registerTopicDlx("钱七", "qianqi");
    }
}
