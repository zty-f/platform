package com.platform.config;


import com.platform.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                // 那些路径不拦截
                .excludePathPatterns(
                        "/api/admin/login",
                        "/api/student/login",
                        "/api/teacher/login",
                        "/api/judge/login",
                        "/api/admin/add",
                        "/api/student/register",
                        "/api/team/add",
                        "/api/school/getIdByName",
                        "/getCurrentUser",
                        "/api/project/selectAll"
                );
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
}
