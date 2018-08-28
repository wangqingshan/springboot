package com.hudong.springboot.exception;

/**
 * NotFoundException
 *
 * @Title: NotFoundException.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 互动百科
 * @Created on 2018/8/23 11:28
 * @Author 90
 */
public class NotFoundException  extends Exception{

    protected String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public NotFoundException() {
        setMessage("404!");
    }

    public NotFoundException(String message) {
        this.message = message;
    }
}
