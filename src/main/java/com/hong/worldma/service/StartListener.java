package com.hong.worldma.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
@Service
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.setProperty ("jsse.enableSNIExtension", "false");
    }
}
