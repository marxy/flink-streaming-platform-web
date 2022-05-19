package com.flink.streaming.web.config;

import com.flink.streaming.web.filter.FlinkAuthFilter;
import com.flink.streaming.web.filter.UserContextFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @Author: quentin.zeng
 * @Date: 2022/5/13 14:43
 */
@Slf4j
@Configuration
public class FilterConfig {

    @Bean
    public FlinkAuthFilter flinkAuthFilter() {
        return new FlinkAuthFilter();
    }

    @Bean
    public FilterRegistrationBean<FlinkAuthFilter> traceIdFilterFilterRegistrationBean(){
        //通过FilterRegistrationBean实例设置优先级可以生效
        //通过@WebFilter无效
        FilterRegistrationBean<FlinkAuthFilter> bean = new FilterRegistrationBean<FlinkAuthFilter>();
        //注册自定义过滤器
        bean.setFilter(flinkAuthFilter());
        //过滤器名称
        bean.setName("flinkAuthFilter");
        //过滤所有路径
        bean.addUrlPatterns("/flink/*");
        //优先级，最顶级
        bean.setOrder(1);
        log.info("init flinkAuthFilter");
        return bean;
    }


    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilterRegistrationBean(){
        //通过FilterRegistrationBean实例设置优先级可以生效
        //通过@WebFilter无效
        FilterRegistrationBean<UserContextFilter> bean = new FilterRegistrationBean<>();
        //注册自定义过滤器
        bean.setFilter(userContextFilter());
        //过滤器名称
        bean.setName("userContextFilter");
        //过滤所有路径
        bean.addUrlPatterns("/*");
        //优先级，最顶级
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        log.info("init userContextFilter");
        return bean;
    }
}
