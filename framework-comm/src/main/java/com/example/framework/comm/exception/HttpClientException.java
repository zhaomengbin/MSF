package com.example.framework.comm.exception;

import com.example.framework.comm.global.ReturnCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * 〈针对HTTP客户端请求发生的异常进行统一封装，方便GlobalExceptionHandler进行统一归类处理〉
 *
 *  主要由系统自动抛出
 *
 * @author zhaomengbin
 * @create 2022/2/18
 * @since 1.0.0
 */
@Data
public class HttpClientException extends RuntimeException{

    private int httpCode;
    private String errCode;
    private String errMsg;
    private Map<String,String> extraInfo;



    public HttpClientException(ReturnCode returnCode,Map extraInfo){
        super(returnCode.value());
        this.httpCode= HttpStatus.BAD_REQUEST.value();
        this.errCode=returnCode.name();
        this.errMsg=returnCode.value();
        this.extraInfo=extraInfo;
    }

    public HttpClientException(String errCode,String errMsg,Map extraInfo){
        super(errMsg);
        this.httpCode= HttpStatus.BAD_REQUEST.value();
        this.errCode=errCode;
        this.errMsg=errMsg;
        this.extraInfo=extraInfo;
    }

    public HttpClientException(int httpCode,String errCode,String errMsg,Map extraInfo){
        super(errMsg);
        this.httpCode= httpCode;
        this.errCode=errCode;
        this.errMsg=errMsg;
        this.extraInfo=extraInfo;
    }
}