package com.pd.distributelock.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RedissonLockController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("redissonLock")
    public String redissonLock() {
        RLock rLock = redissonClient.getLock("order");
        log.info("我进入了方法");
        try {
            // 加锁以后15秒钟自动解锁
            rLock.lock(15, TimeUnit.SECONDS);
            log.info("我获得了锁");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            log.info("我释放了锁");
        }
        log.info("方法执行完成");
        return "方法执行完成";

    }

}
