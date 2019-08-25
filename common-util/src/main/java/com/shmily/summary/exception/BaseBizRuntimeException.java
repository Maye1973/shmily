package com.shmily.summary.exception;

import com.shmily.summary.errorcode.ErrorCode;
import lombok.Data;

import java.util.Objects;

/**
 * 系统业务运行时异常父类
 * create by kevin.ma on 2019/8/6 下午10:38
 **/
public class BaseBizRuntimeException extends RuntimeException implements BaseException {

    private String errorCode;
    private String errorDesc;

    public BaseBizRuntimeException() {
        super();
    }

    public BaseBizRuntimeException(String errorCode, String errorDesc) {
        super(errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizRuntimeException(ErrorCode errorCode) {
        super();
        if (!Objects.isNull(errorCode)) {
            this.errorCode = errorCode.getErrorCode();
            this.errorDesc = errorCode.getErrorDesc();
        }

    }

    public BaseBizRuntimeException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        if (!Objects.isNull(errorCode)) {
            this.errorCode = errorCode.getErrorCode();
            this.errorDesc = errorCode.getErrorDesc();
        }

    }

    public BaseBizRuntimeException(Throwable cause, String errorCode, String errorDesc) {
        super(errorDesc, cause);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizRuntimeException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorDesc) {
        super(errorDesc, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizRuntimeException(String errorDesc, Throwable cause) {
        super(errorDesc, cause);
        this.errorDesc = errorDesc;
    }

    public BaseBizRuntimeException(String errorDesc) {
        super(errorDesc);
        this.errorDesc = errorDesc;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
