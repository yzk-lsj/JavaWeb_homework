package org.example.listener_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;

@WebListener
public class RequestLoggerListener implements ServletRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggerListener.class);

    // 使用 ThreadLocal 来记录每个请求的开始时间，以计算处理时间
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        // 记录请求时间和开始处理时间
        startTime.set(System.currentTimeMillis());

        // 请求时间
        String requestTime = Instant.now().toString();

        // 客户端 IP 地址
        String clientIp = request.getRemoteAddr();

        // 请求方法 (GET, POST等)
        String method = request.getMethod();

        // 请求 URI
        String requestUri = request.getRequestURI();

        // 查询字符串 (如果有)
        String queryString = request.getQueryString() == null ? "" : request.getQueryString();

        // User-Agent
        String userAgent = request.getHeader("User-Agent");

        logger.info("请求初始化: 时间={}, URI={}, 方法={}, 来自 IP={}, 查询字符串={}, User-Agent={}",
                requestTime, requestUri, method, clientIp, queryString, userAgent);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        // 计算处理时间
        long processingTime = System.currentTimeMillis() - startTime.get();

        // 请求 URI
        String requestUri = request.getRequestURI();

        // 请求方法 (GET, POST等)
        String method = request.getMethod();

        // 客户端 IP 地址
        String clientIp = request.getRemoteAddr();

        // 查询字符串 (如果有)
        String queryString = request.getQueryString() == null ? "" : request.getQueryString();

        // User-Agent
        String userAgent = request.getHeader("User-Agent");

        logger.info("请求完成: URI={}, 方法={}, 来自 IP={}, 查询字符串={}, User-Agent={}, 耗时={} ms",
                requestUri, method, clientIp, queryString, userAgent, processingTime);
    }
}
