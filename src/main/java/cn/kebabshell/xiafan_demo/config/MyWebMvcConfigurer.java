package cn.kebabshell.xiafan_demo.config;

import cn.kebabshell.xiafan_demo.interceptor.MySysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by KebabShell
 * on 2020/4/28 下午 05:16
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getSysInterceptor(){
        return new MySysInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //访问日志
        registry.addInterceptor(getSysInterceptor()).addPathPatterns("/**");
    }
}
