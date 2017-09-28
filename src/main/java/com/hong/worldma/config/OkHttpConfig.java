package com.hong.worldma.config;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
@Component
public class OkHttpConfig {

    @Bean
    public okhttp3.OkHttpClient okClient() {
        //System.out.println("----------okhttp3.OkHttpClient");
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
