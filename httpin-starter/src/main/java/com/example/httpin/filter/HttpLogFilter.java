package com.example.httpin.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈注意：需要过滤一些/actuator/*之类的接口〉
 *
 * @author zhaomengbin
 * @create 2022/1/28
 * @since 1.0.0
 */
@Slf4j
public class HttpLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(request.getRequestURI().toString().contains("/actuator/")){
//            return;
//        }
        long start=System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        long end=System.currentTimeMillis();
        log.info("[HttpIn] traceId={}, cost={}ms , url={}", MDC.get("traceId"),end-start,request.getRequestURL());
        responseWrapper.copyBodyToResponse();

    }
}