package org.example.exception;

import org.example.enums.ErrorCodeEnum;

public class ProcessException extends RuntimeException {
    private String errCode;
    private String errMsg;


    public ProcessException() {
        super();
    }
    public ProcessException(String message) {
        super(message);
    }
    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProcessException(Throwable cause) {
        super(cause);
    }
    protected ProcessException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum.getErrMsg());
        this.errCode = errorCodeEnum.getErrCode();
        this.errMsg = errorCodeEnum.getErrMsg();
    }

}
