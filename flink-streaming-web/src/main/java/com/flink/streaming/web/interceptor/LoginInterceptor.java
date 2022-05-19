package com.flink.streaming.web.interceptor;

import com.flink.streaming.web.common.RestResult;
import com.flink.streaming.web.common.holder.UserContextHolder;
import com.flink.streaming.web.common.util.UserSessionUtil;
import com.flink.streaming.web.config.CustomConfig;
import com.flink.streaming.web.model.dto.UserSession;
import com.flink.streaming.web.model.vo.Constant;
import com.flink.streaming.web.service.UserService;
import com.flink.streaming.web.utils.WebUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuhuipei
 * @Description:
 * @date 2020-07-10
 * @time 01:27
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private static final String HOME = "index.html";

    @Autowired
    private UserService userService;

    @Autowired
    private CustomConfig customConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String contextPath = customConfig.getWebContextPath();
        if (contextPath != null && !contextPath.endsWith("/")) {
            contextPath = contextPath + "/";
        } else {
            contextPath = "/";
        }
        log.debug("进入LoginInterceptor拦截器 {}", request.getRequestURI());
        if (request.getRequestURI().equals(contextPath) ||  request.getRequestURI().equals(contextPath + HOME)) {
            return true;
        }
        UserSession userSession = UserContextHolder.get();
        if (userSession == null) {
            if (WebUtil.isAjaxRequest(request)) {
                RestResult<Object> respdata = RestResult.newInstance(Constant.RESPONE_STATUS_UNAUTH, "未登录认证！", null);
                WebUtil.restResponseWithFlush(response, respdata);
            } else {
                response.sendRedirect(contextPath + HOME);
            }
            return false;
        }

        log.debug("未知请求={}", request.getRequestURI());
        return true;
    }

}
