package com.hong.worldma.service.wx;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-13
 */
@Service
public class WxThreadPool {

    public static ExecutorService cachedThreadPool;
    static {
        cachedThreadPool = Executors.newCachedThreadPool();
        //       System.out.println("-----------初始化线程池---------------");
    }
}
