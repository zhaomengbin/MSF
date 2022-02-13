package com.example.framework.comm.reporter.policy;

import com.example.framework.comm.reporter.Reproter;
import com.example.framework.comm.reporter.InputData;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈通过Http方式上报〉
 *
 * @author zhaomengbin
 * @create 2022/2/10
 * @since 1.0.0
 */
@Slf4j
public class HttpReproter implements Reproter {

    @Override
    public void report(InputData inputData) {
        log.info("通过Http方式上报:{}",inputData);
    }
}