package com.example.httpout.cooike;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.Collections;
import java.util.List;

/**
 * 〈okhttp 支持cookie功能〉
 *
 * @author zhaomengbin
 * @create 2020/6/24
 * @since 1.0.0
 */
public class CookieJarService implements CookieJar {

    /**
     * cookies 存储,默认256个
     */
    private LRUCache<String, List<Cookie>> cookieStore;

    public CookieJarService(int maxHostCookieSize) {
        cookieStore = new LRUCache(maxHostCookieSize);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookieStore.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : Collections.emptyList();
    }
}
