package com.zzx.login.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "interceptor.token")
public class TokenProperty {
    private boolean enable;
    private long expire;
    private String key;
    private String pathPatterns;
    private String excludePath;
}
