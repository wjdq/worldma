package com.hong.worldma.service.wx;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-14
 */
@Service
public class WxHashMap {

    public static ConcurrentHashMap<String, String> retcodeMap;
    static {
        retcodeMap = new ConcurrentHashMap<String, String>();
    }
}
