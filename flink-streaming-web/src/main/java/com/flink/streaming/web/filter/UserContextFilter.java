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
 * @Date: 2022/5/19 19:41
 */
@Slf4j
public class UserContextFilter extends OncePerRequestFilter {

    @Autowired
    private CustomConfig customConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserSession userSession = UserSessionUtil.userSession(request, customConfig.getJwtSignatureKey());
        try {
            UserContextHolder.set(userSession);
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.remove();
        }
    }
}
