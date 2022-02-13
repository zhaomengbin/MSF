package com.example.httpout.sentinel;

import com.alibaba.csp.sentinel.adapter.okhttp.fallback.OkHttpFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Connection;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhaoyuguang
 */
@Slf4j
public class DemoOkhttpFallBack implements OkHttpFallback {


    @Override
    public Response handle(Request request, Connection connection, BlockException e) {
        // Just wrap and throw the exception.
        log.error("请求{}失败,ex:{}",request.url(),e);
        throw new SentinelRpcException(e);
    }
}
