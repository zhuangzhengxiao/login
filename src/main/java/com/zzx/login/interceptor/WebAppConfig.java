package com.zzx.login.interceptor;


import com.zzx.login.config.TokenProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加拦截器
 * 将配置好的token拦截器加进来
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    TokenProperty tokenProperty; // 获取配置

    @Autowired
    TokenInterceptor tokenInterceptor; // 具体操作的token拦截器

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("Token拦截配置 ==========>" + tokenProperty);

        //是否开启拦截
        if (tokenProperty.isEnable()) {
            //配置token拦截path
            String[] excludePathArray = tokenProperty.getExcludePath().split(";");
            String[] pathPatternArray = tokenProperty.getPathPatterns().split(";");

            registry.addInterceptor(tokenInterceptor)
                    .addPathPatterns(pathPatternArray)
                    .excludePathPatterns(excludePathArray);
        }
    }
}