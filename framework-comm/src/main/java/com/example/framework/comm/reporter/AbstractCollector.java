package com.example.framework.comm.reporter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈上报器〉
 *  默认通过http上报，若有MQ,则使用MQ上报
 * @author zhaomengbin
 * @create 2022/2/10
 * @since 1.0.0
 */
@Slf4j
@Setter
public abstract class AbstractCollector {

    private Reproter reproter;
    /**
     * 采集数据
     */
    public  abstract InputData collectData();

    /**
     * 上报数据
     */
    public void reportData(InputData inputData){
        reproter.report(inputData);
    }

}