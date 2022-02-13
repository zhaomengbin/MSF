package com.example.httpout.sentinel;

import com.alibaba.csp.sentinel.adapter.okhttp.extractor.OkHttpResourceExtractor;
import okhttp3.Connection;
import okhttp3.Request;

/**
 * @author zhaoyuguang
 */
public class DemoOkHttpResourceExtractor implements OkHttpResourceExtractor {

    @Override
    public String extract(Request request, Connection connection) {
        return request.method() + ":" + request.url().toString();
    }
}