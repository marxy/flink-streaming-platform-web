package com.flink.streaming.web.filter;

import com.flink.streaming.web.common.util.UserSessionUtil;
import com.flink.streaming.web.model.dto.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

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

    private static final String HOME = "/";

    @Autowired
    private ServerProperties serverProperties;

    private String homeUrl;

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        String contextPath = serverProperties.getServlet().getContextPath();
        if (contextPath != null) {
            homeUrl = contextPath;
            if (!contextPath.endsWith(HOME)) {
                homeUrl += HOME;
            }
        } else {
            homeUrl = HOME;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserSession userSession = UserSessionUtil.userSession(request);
        if (userSession != null) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(homeUrl);
        }
    }
}
