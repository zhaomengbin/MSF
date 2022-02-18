package com.example.framework.comm.exception;

import com.example.framework.comm.global.ReturnCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 〈客户端异常〉
 *
 * @author zhaomengbin
 * @create 2022/2/9
 * @since 1.0.0
 */
@Data
public class BizClientException extends RuntimeException{

    private int httpCode;
    private String errCode;
    private String errMsg;



    public BizClientException(ReturnCode returnCode){
        super(returnCode.value());
        this.httpCode= HttpStatus.BAD_REQUEST.value();
        this.errCode=returnCode.name();
        this.errMsg=returnCode.value();
    }

    public BizClientException(String errCode, String errMsg){
        super(errMsg);
        this.httpCode= HttpStatus.BAD_REQUEST.value();
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    public BizClientException(int httpCode, String errCode, String errMsg){
        super(errMsg);
        this.httpCode= httpCode;
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

}