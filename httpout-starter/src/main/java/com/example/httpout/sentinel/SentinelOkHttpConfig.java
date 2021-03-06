package com.example.httpout.sentinel;

import com.alibaba.csp.sentinel.adapter.okhttp.extractor.DefaultOkHttpResourceExtractor;
import com.alibaba.csp.sentinel.adapter.okhttp.extractor.OkHttpResourceExtractor;
import com.alibaba.csp.sentinel.adapter.okhttp.fallback.DefaultOkHttpFallback;
import com.alibaba.csp.sentinel.adapter.okhttp.fallback.OkHttpFallback;
import com.alibaba.csp.sentinel.util.AssertUtil;

public class SentinelOkHttpConfig {

    public static final String DEFAULT_RESOURCE_PREFIX = "okhttp:";

    private final String resourcePrefix;
    private final OkHttpResourceExtractor resourceExtractor;
    private final OkHttpFallback fallback;

    public SentinelOkHttpConfig() {
        this(DEFAULT_RESOURCE_PREFIX);
    }

    public SentinelOkHttpConfig(String resourcePrefix) {
        this(resourcePrefix, new DefaultOkHttpResourceExtractor(), new DefaultOkHttpFallback());
    }

    public SentinelOkHttpConfig(OkHttpResourceExtractor resourceExtractor, OkHttpFallback fallback) {
        this(DEFAULT_RESOURCE_PREFIX, resourceExtractor, fallback);
    }

    public SentinelOkHttpConfig(String resourcePrefix,
                                OkHttpResourceExtractor resourceExtractor,
                                OkHttpFallback fallback) {
        AssertUtil.notNull(resourceExtractor, "resourceExtractor cannot be null");
        AssertUtil.notNull(fallback, "fallback cannot be null");
        this.resourcePrefix = resourcePrefix;
        this.resourceExtractor = resourceExtractor;
        this.fallback = fallback;
    }

    public String getResourcePrefix() {
        return resourcePrefix;
    }

    public OkHttpResourceExtractor getResourceExtractor() {
        return resourceExtractor;
    }

    public OkHttpFallback getFallback() {
        return fallback;
    }

    @Override
    public String toString() {
        return "SentinelOkHttpConfig{" +
            "resourcePrefix='" + resourcePrefix + '\'' +
            ", resourceExtractor=" + resourceExtractor +
            ", fallback=" + fallback +
            '}';
    }
}