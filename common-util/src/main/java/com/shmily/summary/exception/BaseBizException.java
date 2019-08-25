package com.shmily.summary.exception;

import com.shmily.summary.errorcode.ErrorCode;

import java.util.Objects;

/**
 * 系统业务未检异常父类
 * create by kevin.ma on 2019/8/24 下午7:54
 **/
public class BaseBizException extends Exception implements BaseException {

    private String errorCode;
    private String errorDesc;


    public BaseBizException() {
        super();
    }

    public BaseBizException(String errorCode, String errorDesc) {
        super(errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizException(ErrorCode errorCode) {
        super();
        if (!Objects.isNull(errorCode)) {
            this.errorCode = errorCode.getErrorCode();
            this.errorDesc = errorCode.getErrorDesc();
        }

    }

    public BaseBizException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        if (!Objects.isNull(errorCode)) {
            this.errorCode = errorCode.getErrorCode();
            this.errorDesc = errorCode.getErrorDesc();
        }

    }

    public BaseBizException(Throwable cause, String errorCode, String errorDesc) {
        super(errorDesc, cause);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorDesc) {
        super(errorDesc, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizException(String errorDesc, Throwable cause) {
        super(errorDesc, cause);
        this.errorDesc = errorDesc;
    }

    public BaseBizException(String errorDesc) {
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
