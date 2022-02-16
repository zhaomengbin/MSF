package com.example.httpin.filter;

import com.example.framework.comm.threadlocal.ParameterThreadLocal;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/8
 * @since 1.0.0
 */
public class ParameterFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ParameterThreadLocal.clear();
        if(ParameterThreadLocal.ttlMap.size()==0){
           return;
        }
        /**
         * p_* => ThreadLocal的变量 => set value
         */
        Enumeration<String> params=request.getParameterNames();
        while (params.hasMoreElements()){
            String parameter=params.nextElement();
            if(ParameterThreadLocal.ttlMap.containsKey(parameter)){
                ParameterThreadLocal.ttlMap.get(parameter).set(request.getParameter(parameter));
            }
        }

        filterChain.doFilter(request, response);

    }
}