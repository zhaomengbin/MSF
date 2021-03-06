package com.example.framework.comm.exception;

import com.example.framework.comm.global.ReturnCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 〈服务端异常〉
 *
 * @author zhaomengbin
 * @create 2022/2/9
 * @since 1.0.0
 */
@Data
public class BizServerException extends RuntimeException{
    private int httpCode;
    private String errCode;
    private String errMsg;



    public BizServerException(ReturnCode returnCode){
        super(returnCode.value());
        this.httpCode= HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errCode=returnCode.name();
        this.errMsg=returnCode.value();
    }

    public BizServerException(String errCode, String errMsg){
        super(errMsg);
        this.httpCode= HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    public BizServerException(int httpCode, String errCode, String errMsg){
        super(errMsg);
        this.httpCode= httpCode;
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

}