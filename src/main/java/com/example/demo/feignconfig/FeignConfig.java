package com.example.demo.feignconfig;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor interceptor() {
        return template -> {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes();

            if (attrs != null) {
                String token =
                        attrs.getRequest().getHeader("Authorization");

                if (token != null) {
                    template.header("Authorization", token);
                }
            }
        };
    }
}