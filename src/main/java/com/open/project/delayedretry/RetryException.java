package com.open.project.delayedretry;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:59
 * @Description 重试异常
 */
@Slf4j
public class RetryException extends RuntimeException{

    public RetryException() {
        super();
    }

    public RetryException(String message) {
        super(message);
    }

    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryException(Throwable cause) {
        super(cause);
    }

    protected RetryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
