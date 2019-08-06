package com.shmily.summary.exception;

import com.shmily.summary.errorcode.ErrorCode;
import lombok.Data;

import java.util.Objects;

/**
 * 系统业务异常父类
 * create by kevin.ma on 2019/8/6 下午10:38
 **/
@Data
public class BaseBizException extends RuntimeException {

    private String errorCode;
    private String errorDesc;

    public BaseBizException() {
    }

    public BaseBizException(String errorCode, String errorDesc) {
        super(errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BaseBizException(ErrorCode errorCode) {
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
}
