package net.runningcoder.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by wangmaocheng on 2017/10/13.
 */
@Slf4j
@Service
public class TestService {

    public String hello() {
        log.info("开始执行hello方法...");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("执行hello方法结束...");
        return "hello world";
    }
}
