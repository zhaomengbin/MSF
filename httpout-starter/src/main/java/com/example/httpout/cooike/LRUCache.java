package com.example.httpout.cooike;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈一种简单的LRU实现〉
 *
 * @author zhaomengbin
 * @create 2020/6/24
 * @since 1.0.0
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    /**LRU 最大容量*/
    private  int maxSize=0;
    /**LinkedHashMap 非线程安全*/
    private transient Lock lock = new ReentrantLock();

    public LRUCache(int cacheSize) {
        // initialCapacity、loadFactor都不重要
        // accessOrder要设置为true，按访问排序
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        maxSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        // 超过阈值时返回true，进行LRU淘汰
        return size() > maxSize;
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
