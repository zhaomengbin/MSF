package com.example.framework.comm.reporter;

/**
 * 〈上报器〉
 *
 * @author zhaomengbin
 * @create 2022/2/10
 * @since 1.0.0
 */
public interface Reproter {
    /**
     * 上报本地
     * @param inputData
     */
   void report(InputData inputData);
}