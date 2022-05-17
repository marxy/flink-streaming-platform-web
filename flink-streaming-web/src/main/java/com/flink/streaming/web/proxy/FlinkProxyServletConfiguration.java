package com.flink.streaming.web.proxy;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * @Author: quentin.zeng
 * @Date: 2022/5/13 14:18
 */
@Configuration
public class FlinkProxyServletConfiguration implements EnvironmentAware {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        Properties properties= (Properties) bindResult.get();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), properties.getProperty("servlet-url"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_TARGET_URI, properties.getProperty("target-url"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, properties.getProperty("logging-enabled", "false"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_MAXCONNECTIONS, properties.getProperty("max-connections", "100"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_PRESERVEHOST, properties.getProperty("preserve-host", "false"));
        servletRegistrationBean.addInitParameter(ProxyServlet.P_PRESERVECOOKIES, properties.getProperty("preserve-cookies", "true"));
        return servletRegistrationBean;
    }

    private BindResult bindResult;

    @Override
    public void setEnvironment(Environment environment) {
        Iterable sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        BindResult bindResult = binder.bind("proxy.flink", Properties.class);
        this.bindResult = bindResult;
    }
}