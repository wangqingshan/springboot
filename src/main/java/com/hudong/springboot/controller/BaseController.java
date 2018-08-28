package com.hudong.springboot.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * @description：基础 controller
 * @author：JBL
 * @date：2015/10/1 14:51
 */
public abstract class BaseController {

      @Value("${pro.domainUrl}")
      public  String domainUrl;



}
