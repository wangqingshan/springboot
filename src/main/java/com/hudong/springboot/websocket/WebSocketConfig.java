package com.hudong.springboot.websocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * WebSocketConfig
 *
 * @Title: WebSocketConfig.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 互动百科
 * @Created on 2018/8/27 10:02
 * @Author 90
 */


@Configuration
public class WebSocketConfig {

    //配置ServerEndpointExporter的实例到springboot容器中，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint为自己的子类
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
