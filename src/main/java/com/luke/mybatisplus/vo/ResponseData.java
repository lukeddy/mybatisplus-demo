package com.luke.mybatisplus.vo;

import com.luke.mybatisplus.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID=1l;
    private long code;
    private T data;
    private String msg;

    public ResponseData(){}

    public ResponseData(ErrorCode errorCode){
        errorCode= Optional.ofNullable(errorCode).orElse(ErrorCode.FAILED);
        this.code=errorCode.getCode();
        this.msg=errorCode.getMsg();
    }

    public static <T> ResponseData<T> ok(T data){
        ErrorCode errorCode=ErrorCode.SUCCESS;
        if(data instanceof Boolean && Boolean.FALSE.equals(data)){
            errorCode=ErrorCode.FAILED;
        }
       return restResult(data,errorCode);
    }

    public static <T> ResponseData<T> failed(String msg) {
        return restResult(null,ErrorCode.FAILED.getCode(), msg);
    }
    public static <T> ResponseData<T> failed(ErrorCode errorCode) {
        return restResult(null, errorCode);
    }

    public static <T> ResponseData<T> restResult(T data, ErrorCode errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }
    private static <T> ResponseData<T> restResult(T data, long code, String msg) {
        ResponseData<T> apiResult = new ResponseData<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
