package com.peng1m.springboot.filter;

import com.peng1m.springboot.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String jwtTokenCookieName = "JWT_TOKEN";
    private static final String key = "KEY";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String username = JwtUtil.parseToken(httpServletRequest, jwtTokenCookieName, key);
        if (username == null){
            httpServletResponse.sendRedirect("login");
        }else {
            httpServletRequest.setAttribute("username", username);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
