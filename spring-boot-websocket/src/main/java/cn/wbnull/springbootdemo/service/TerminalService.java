package cn.wbnull.springbootdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TerminalService
 *
 * @author dukunbiao(null)  2022-07-02
 * https://github.com/dkbnull/SpringBootDemo
 */
@Service
@Slf4j
public class TerminalService {

    public void terminal() {
        log.info("这是一条测试日志");
    }
}
