package com.flink.streaming.web.filter;

import com.flink.streaming.web.common.holder.UserContextHolder;
import com.flink.streaming.web.common.util.UserSessionUtil;
import com.flink.streaming.web.config.CustomConfig;
import com.flink.streaming.web.model.dto.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: quentin.zeng
 * @Date: 2022/5/13 14:41
 */
@Slf4j
public class FlinkAuthFilter extends OncePerRequestFilter {

    private static final String HOME = "index.html";

    @Autowired
    private CustomConfig customConfig;

    private String homeUrl;

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        String contextPath = customConfig.getWebContextPath();
        if (contextPath != null && !contextPath.endsWith("/")) {
            contextPath = contextPath + "/";
        } else {
            contextPath = "/";
        }
        homeUrl = contextPath + HOME;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserSession userSession = UserContextHolder.get();
        if (userSession != null) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(homeUrl);
        }
    }
}
