package com.chriniko.example.interceptor;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class DummyFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        // Note: print all request parameters
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = req.getParameter(parameterName);

            System.out.println("{" + parameterName + " : " + parameterValue + "}");
        }

        // Note: print all cookies
        for (Cookie cookie : req.getCookies()) {
            System.out.println("{" + cookie.getName() + " : " + cookie.getValue() + "}");
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        // we can close resources here
    }
}
