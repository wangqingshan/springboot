package com.hudong.springboot.exception;

/**
 * NullOrEmptyException
 *
 * @Title: NullOrEmptyException.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 互动百科
 * @Created on 2018/8/23 9:54
 * @Author 90
 */
public class NullOrEmptyException extends Exception{
    protected String message;
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public NullOrEmptyException() {
        setMessage("Parameter is null or empty!");
    }

    public NullOrEmptyException(String message) {
        this.message = message;
    }

}
