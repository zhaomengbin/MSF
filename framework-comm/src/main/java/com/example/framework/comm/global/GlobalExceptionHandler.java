package com.example.framework.comm.global;

import com.example.framework.comm.exception.ClientException;
import com.example.framework.comm.exception.ServerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 〈全局异常捕获〉
 *
 * @author zhaomengbin
 * @create 2022/2/9
 * @since 1.0.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    /**
     * 自定义异常：Client端
     * @param e
     * @return
     */
    @ExceptionHandler(value = ClientException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ClientException e){
        int httpCode=e.getHttpCode();
        if(httpCode==0){
            httpCode=HttpStatus.BAD_REQUEST.value();
        }
        ErrorResponse errorResponse=this.init(httpCode,e.getErrCode(),e.getErrMsg());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(httpCode));

    }

    /**
     * 自定义异常：server端
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ServerException e){
        int httpCode=e.getHttpCode();
        if(httpCode==0){
            httpCode=HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        ErrorResponse errorResponse=this.init(httpCode,e.getErrCode(),e.getErrMsg());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(httpCode));

    }

    /**
     * 属性valid不通过场景
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        List<String> list = new LinkedList<>();
        result.getFieldErrors().forEach(error -> {
            String field = error.getField();
            Object value = error.getRejectedValue();
            String msg = error.getDefaultMessage();
            list.add(String.format("(%s=%s : %s)", field, value, msg));
        });
        ErrorResponse errorResponse=this.init(HttpStatus.BAD_REQUEST.value(),ReturnCode.InvalidParam.name(),String.format("校验不通过=> %s",list.toString()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    /**
     * Web请求异常，一般是4XX
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ServletException.class,HttpMessageNotReadableException.class,TypeMismatchException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e){
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse= this.init(httpStatus.value(),ReturnCode.InvalidParam.name(),e.getMessage());
        /**
         * 404
         * 针对查找不到路径的请求，若期望抛异常，需增加如下properties配置
         *  - spring.mvc.throw-exception-if-no-handler-found=true
         *  - spring.web.resources.add-mappings=false
         */
        if(e instanceof NoHandlerFoundException){
            httpStatus=HttpStatus.NOT_FOUND;
            errorResponse.setHttpStatus(httpStatus.value());
        }
        /**
         * 503 : 服务超负荷运载
         */
        if(e instanceof UnavailableException){
            httpStatus=HttpStatus.SERVICE_UNAVAILABLE;
            errorResponse.setHttpStatus(httpStatus.value());
        }
        return new ResponseEntity<>(errorResponse, httpStatus);
    }


    /**
     * 兜底：处理所有未知类型的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Throwable e){
        ErrorResponse errorResponse=this.init(HttpStatus.UNPROCESSABLE_ENTITY.value(),ReturnCode.UnKnownError.name(),e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 设置公共属性
     * @return
     */
    private ErrorResponse init(int httpCode, String returnCode, String returnMsg){
        ErrorResponse errorResponse= ErrorResponse.builder()
                .httpStatus(httpCode)
                .returnCode(returnCode)
                .returnMsg(returnMsg)
                .build();
        Map extra=new HashMap();
        if(StringUtils.isNotBlank(MDC.get("traceId"))){
            extra.put("traceId",MDC.get("traceId"));
            errorResponse.setExtraInfo(extra);
        }
        return errorResponse;
    }

}