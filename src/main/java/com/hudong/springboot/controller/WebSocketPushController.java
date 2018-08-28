package com.hudong.springboot.controller;

import com.hudong.springboot.service.TLiveInfoService;
import com.hudong.springboot.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * WebSocketPushController
 *
 * @Title: WebSocketPushController.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 互动百科
 * @Created on 2018/8/27 11:07
 * @Author 90
 */
@Controller
@RequestMapping("/")
public class WebSocketPushController {

    @Autowired
    private TLiveInfoService tLiveInfoService;
    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return cid+"#"+e.getMessage();
        }
        return "success";
    }


}
