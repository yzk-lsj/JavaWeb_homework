package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * LoginFilter 实现了对用户登录状态的验证，拦截所有请求，
 * 如果用户未登录则重定向至登录页面。允许对特定的 URL 路径进行免登录访问。
 */
@WebFilter("/*")  // 应用于所有 URL
public class LoginFilter implements Filter {

    /**
     * 免登录访问的 URL 列表，如登录页面、注册页面和公共资源。
     */
    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/login", "/register", "/register.jsp", "/public");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获取当前请求路径
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // 获取 session，不创建新 session
        HttpSession session = httpRequest.getSession(false);

        // 如果当前请求路径属于免登录访问的路径，则放行请求
        if (isExcludedPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 检查 session 中是否有 "user" 属性（表示用户已登录）
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response); // 用户已登录，继续处理请求
        } else {
            // 用户未登录，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // 过滤器销毁操作
    }

    /**
     * 判断请求路径是否在免登录访问的路径列表中。
     *
     * @param path 当前请求的路径
     * @return 如果请求路径属于免登录访问的路径列表，则返回 true，否则返回 false
     */
    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}
