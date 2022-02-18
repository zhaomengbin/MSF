package com.example.httpout.interceptor;

import com.example.framework.comm.exception.HttpClientException;
import com.example.framework.comm.global.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/1/28
 * @since 1.0.0
 */
@Slf4j
public class OkHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
       String url=request.url().toString();
        log.info("traceId={}, url ={}", MDC.get("traceId"),request.url());
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
                Map<String,String> extraInfo=new ConcurrentHashMap<>();
                extraInfo.put("Url",url);
                String errMsg=e.getMessage();
                if(e.getCause()!=null){
                    errMsg=e.getCause().getMessage();
                }
                if(e instanceof SocketTimeoutException){
                    throw new HttpClientException(HttpStatus.REQUEST_TIMEOUT.value(), ReturnCode.Timeout.name(),errMsg,extraInfo);
                }else {
                    throw new HttpClientException(HttpStatus.REQUEST_TIMEOUT.value(), ReturnCode.Timeout.name(),errMsg,extraInfo);
                }

        }

        return response;
    }
}