package rateLimit.limit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.util.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private LimiterService limiterService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        if(!limiterService.isAllowed(clientIp)) {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded . Try after 1 minute" );
            return false;
        }
        return true;
    }
}
