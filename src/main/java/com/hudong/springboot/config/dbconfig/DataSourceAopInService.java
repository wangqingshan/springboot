package com.hudong.springboot.config.dbconfig;

import org.springframework.core.PriorityOrdered;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.hudong.springboot.config.dbconfig.DataSourceContextHolder;

/**
 * DataSourceAopInService
 *
 * @Title: DataSourceAopInService.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 互动百科
 * @Created on 2018/8/23 17:11
 * @Author 90
 */
@Aspect
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
@Component
public class DataSourceAopInService implements PriorityOrdered {



    private static Logger log = LoggerFactory.getLogger(DataSourceAopInService.class);

    @Before("execution(* com.hudong.springboot.service..*.find*(..)) "
            + " or execution(* com.hudong.springboot.service..*.get*(..)) "
            + " or execution(* com.hudong.springboot.service..*.query*(..))"
            + " or execution(* com.hudong.springboot.service..*.list*(..))"
            + " and @annotation(com.hudong.springboot.annotation.ReadDataSource) ")
    public void setReadDataSourceType() {
        //如果已经开启写事务了，那之后的所有读都从写库读
        if(!DataSourceType.write.getType().equals(DataSourceContextHolder.getReadOrWrite())){
            DataSourceContextHolder.setRead();
        }

    }

    @Before("execution(* com.hudong.springboot.service..*.insert*(..)) "
            + " or execution(* com.hudong.springboot.service..*.add*(..))"
            + " or execution(* com.hudong.springboot.service..*.save*(..))"
            + " or execution(* com.hudong.springboot.service..*.create*(..))"
            + " or execution(* com.hudong.springboot.service..*.update*(..))"
            + " or execution(* com.hudong.springboot.service..*.mod*(..))"
            + " or execution(* com.hudong.springboot.service..*.delete*(..))"
            + " or execution(* com.hudong.springboot.service..*.del*(..))"
            + " or execution(* com.hudong.springboot.service..*.truncate*(..))"
            + " and @annotation(com.hudong.springboot.annotation.WriteDataSource) ")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.setWrite();
    }

    @Override
    public int getOrder() {
        /**
         * 值越小，越优先执行
         * 要优于事务的执行
         * 在启动类中加上了@EnableTransactionManagement(order = 10)
         */
        return 1;
    }

}
