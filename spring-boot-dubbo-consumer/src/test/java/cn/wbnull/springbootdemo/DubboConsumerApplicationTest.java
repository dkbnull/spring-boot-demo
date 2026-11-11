package cn.wbnull.springbootdemo;

import cn.wbnull.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DubboConsumerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class DubboConsumerApplicationTest {

    @Autowired
    public UserService userService;

    @Test
    public void contextLoads() {
        String userInfo = userService.getUserInfo();
        log.info("用户信息：{}", userInfo);
    }
}
