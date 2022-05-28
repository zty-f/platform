package com.platform.filter;


import com.platform.base.ResponseCode;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Hchier
 */
@WebFilter(urlPatterns = {"/api/*"}, filterName = "LoginFilter")
public class LoginFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login", "/register")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        System.out.println("doFilter doFilter doFilter");

        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        Iterator<String> it = ALLOWED_PATHS.iterator();
        boolean allowedPath = false;
        while (it.hasNext()) {
            allowedPath = path.contains(it.next());
            if (allowedPath) {
                break;
            }
        }
        System.out.println("path:  " + path + "   .allowedPath:  " + allowedPath);

        if (!allowedPath && httpServletRequest.getSession().getAttribute("username") == null) {
            httpServletResponse.getWriter().println("" +
                    "{\n" +
                    "    \"code\": \"" + ResponseCode.NOT_LOGGED_IN.getCode() + "\",\n" +
                    "    \"message\": \"" + ResponseCode.NOT_LOGGED_IN.getMessage() + "\"\n" +
                    "}");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        System.out.println();

    }

    @Override
    public void destroy() {

    }
}
