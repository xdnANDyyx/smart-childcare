package com.zhyyt.nursery.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 全局登录拦截
            SaRouter.match("/**")
                    .notMatch(
                            "/auth/login",
                            "/auth/captcha",
                            "/auth/register",
                            "/doc.html",
                            "/swagger-resources/**",
                            "/webjars/**",
                            "/v3/api-docs/**",
                            "/favicon.ico",
                            "/error"
                    )
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}
