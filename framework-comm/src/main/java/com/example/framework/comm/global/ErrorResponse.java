package com.example.framework.comm.global;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 〈统一异常响应〉
 *  TODO 增加辅助信息，如请求url、traceId、requestId、uid等
 *
 * @author zhaomengbin
 * @create 2022/2/9
 * @since 1.0.0
 */
@Data
@Builder
public class ErrorResponse {
    private int httpStatus;
    private String returnCode;
    private String returnMsg;
    private Map<String,String> extraInfo;

}