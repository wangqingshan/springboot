package com.hudong.springboot.exception;

import com.hudong.springboot.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * GlobalExceptionHandler
 *
 * @Title: GlobalExceptionHandler.java
 * @Copyright: Copyright (c) 2005
 * @Description:使用 @ControllerAdvice 处理异常也有一定的 局限性。只有进入 Controller 层的错误，
 * 才会由 @ControllerAdvice 处理。拦截器 抛出的错误，以及 访问错误地址 的情况 @ControllerAdvice 处理不了，
 * 由 Spring Boot 默认的 异常处理机制 处理。
 * @Company: 互动百科
 * @Created on 2018/8/23 11:16
 * @Author 90
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MailService mailService;
    @ExceptionHandler(NullOrEmptyException.class)
    @ResponseBody
    public ErrorMessage<String> nullOrEmptyExceptionHandler(HttpServletRequest request, NullOrEmptyException exception) throws Exception {
        return handleErrorInfo(request, exception.getMessage(), exception);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage<String> exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        String msg=exception.getMessage();
        if(exception instanceof ArithmeticException){//自定义msg
            msg= "发生了除0异常";
        }
        //发邮件
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        //发送邮件
        mailService.sendSimpleMail("1287265008@qq.com", "项目异常", sw.toString());

        //发邮件结束
        return handleErrorInfo(request, msg, exception);
    }

    /*@ExceptionHandler(value=NotFoundException.class)
    ModelAndView notFoundExceptionHandler(NotFoundException exception,HttpServletRequest request){
        //进行页面跳转
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error.html");
        modelAndView.addObject("msg", exception.getMessage());
        return modelAndView;
    }*/

    private ErrorMessage<String> handleErrorInfo(HttpServletRequest request, String message, Exception exception) {
        ErrorMessage<String> errorMessage = new ErrorMessage<>();
        errorMessage.setMessage(message);
        errorMessage.setCode(ErrorMessage.ERROR);
        errorMessage.setData(message);
        errorMessage.setUrl(request.getRequestURL().toString());
        return errorMessage;
    }


}
