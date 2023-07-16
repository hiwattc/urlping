package com.staroot.urlping.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 로그인 체크를 위한 로직을 구현합니다.
        // 예를 들어, 세션에 로그인 정보가 있는지 확인할 수 있습니다.
        // 이 예제에서는 로그인 상태를 확인하기 위해 "isLoggedIn"이라는 속성이 세션에 존재하는지 확인합니다.
        boolean isLoggedIn = request.getSession().getAttribute("isLoggedIn") != null;

        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트합니다.
        if (!isLoggedIn) {
            logger.debug("User not login: {}", isLoggedIn);
            //response.sendRedirect("/login");
        }else{
            logger.debug("User login ok: {}", isLoggedIn);
        }

        // 로그인이 되어 있는 경우 요청을 그대로 진행합니다.
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 생략 가능한 메서드들입니다.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 로직을 구현할 수 있습니다.
    }

    @Override
    public void destroy() {
        // 필터 종료 시 호출되는 로직을 구현할 수 있습니다.
    }
}

