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
     * 免登录访问的 URL 列表，包含不需要用户登录即可访问的页面路径。
     * 例如登录页面、注册页面以及公共资源路径等。
     */
    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/login", "/register", "/register.jsp", "/public");

    /**
     * 初始化方法，可用于在过滤器初始化时执行资源的加载或配置。
     *
     * @param filterConfig 过滤器配置对象
     * @throws ServletException 可能抛出的 Servlet 异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化，当前没有需要特别处理的内容
    }

    /**
     * 核心过滤逻辑。用于验证用户是否已登录，未登录的用户将被重定向到登录页面。
     *
     * @param request  客户端发来的 Servlet 请求
     * @param response 服务端发送的 Servlet 响应
     * @param chain    过滤器链，用于将请求传递给下一个过滤器或资源
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 将通用 ServletRequest 转换为 HttpServletRequest，以使用 HTTP 特有的方法
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获取当前请求的相对路径（去掉上下文路径部分）
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // 获取当前的 HTTP session，不创建新的 session（避免不必要的 session 创建）
        HttpSession session = httpRequest.getSession(false);

        // 如果请求路径属于免登录访问的路径，则直接放行
        if (isExcludedPath(path)) {
            chain.doFilter(request, response); // 继续请求链的处理
            return; // 提前返回，不执行后续登录检查
        }

        // 检查用户是否已登录，即 session 中是否存在 "user" 属性
        if (session != null && session.getAttribute("user") != null) {
            // 用户已登录，继续处理请求
            chain.doFilter(request, response);
        } else {
            // 用户未登录，重定向到登录页面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    /**
     * 过滤器销毁方法，在容器销毁过滤器实例时调用。
     * 可用于清理资源。
     */
    @Override
    public void destroy() {
        // 过滤器销毁，当前没有需要特别处理的资源清理
    }

    /**
     * 判断当前请求路径是否属于免登录访问的路径列表中。
     *
     * @param path 当前请求的路径
     * @return 如果请求路径属于免登录访问的路径列表，则返回 true，否则返回 false
     */
    private boolean isExcludedPath(String path) {
        // 遍历 EXCLUDED_PATHS 列表，逐个检查是否以请求路径开头
//        if (EXCLUDED_PATHS != null) {
//            for (String excludedPath : EXCLUDED_PATHS) {
//                if (path.startsWith(excludedPath)) {
//                    return true; // 如果匹配到免登录路径，则返回 true
//                }
//            }
//        }
//        return false; // 如果没有匹配的路径，则返回 false
//
//
        return EXCLUDED_PATHS != null && EXCLUDED_PATHS.stream().anyMatch(path::startsWith);

//        EXCLUDED_PATHS != null：
//        这个检查是为了防止 EXCLUDED_PATHS 列表为空（即 null），确保我们不会对空列表进行操作。如果 EXCLUDED_PATHS 为 null，整个表达式将直接返回 false，不会执行后面的 Stream 操作。
//        
//        EXCLUDED_PATHS.stream()：
//        这部分将 EXCLUDED_PATHS 列表转换为 Java 8 的 Stream。Stream 是一种方便处理集合操作的工具，它提供了一系列方法来简化集合处理。
//        
//        anyMatch(path::startsWith)：
//        anyMatch 是 Stream 的一种操作方法，用于检查 Stream 中是否有任意一个元素满足给定的条件。
//        这里的 path::startsWith 是一个方法引用，表示对于每个 EXCLUDED_PATHS 列表中的路径元素，检查 path 是否以该元素开头。如果 path 以其中任意一个排除路径开头，就返回 true。
    }
}
