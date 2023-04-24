package org.example.cloudskill.handler;

import com.google.common.util.concurrent.RateLimiter;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudentity.domain.R;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenLimiterHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 使用令牌桶算法 限流
        RateLimiter limiter = RateLimiter.create(SystemConfig.SKILL_TOKENS);
        if (limiter.tryAcquire()) {
            // 获取到令牌
            return true;
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(R.ok("亲，秒杀已售罄！"));
            return false;
        }
    }
}
