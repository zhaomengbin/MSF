package com.example.framework.comm.global;

/**
 * 〈HTTP请求返回码〉
 *
 * @author zhaomengbin
 * @create 2022/2/9
 * @since 1.0.0
 */
public enum ReturnCode {
    SUCCESS("请求成功"),
    NotFound("请求资源不存在"),
    InvalidParam("请求参数无效"),
    Timeout("请求超时"),
    ConnectTimeout("请求连接超时"),
    ReadTimeout("请求读超时"),
    InvalidSign("sign签名无效"),
    ServerError("服务内部错误"),
    UnKnownHost("未知的Host"),
    UnKnownError("系统异常");

    private String value;

    ReturnCode(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }


}