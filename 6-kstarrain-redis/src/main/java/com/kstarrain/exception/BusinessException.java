package com.kstarrain.exception;


/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String code, String message){
        super(message);
        this.code = code;
    }

    public BusinessException(IErrorCode errorCode) {
        super(errorCode != null ? errorCode.getDesc() : null);
        this.code = (errorCode != null ? errorCode.getCode() : null);
    }

    public BusinessException(IErrorCode errorCode, Throwable cause) {
        super(errorCode != null ? errorCode.getDesc() : null, cause);
        this.code = (errorCode != null ? errorCode.getCode() : null);
    }


    public String getCode() {
        return code;
    }

}
