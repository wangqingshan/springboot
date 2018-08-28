package com.hudong.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.hudong.springboot.bean.TLiveUser;
import com.hudong.springboot.service.TLiveUserService;
import com.hudong.springboot.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 * @author Lenovo
 */
@Controller
public class TLiveUserController extends BaseController {

	@Autowired
	private TLiveUserService tLiveUserService;
	public static final String SESSION_USER_KEY = "session_user";
	
	//登录页
	@RequestMapping(value = "/")
	public String login(HttpServletRequest request){
		return "/login";
	}
	//登录鉴权
	@RequestMapping(value = "/login")
	@ResponseBody
	public String checkLogin(HttpServletRequest request) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			password = MD5Util.encode2MD5(password);
			TLiveUser tLiveUser = tLiveUserService.queryByName(username);
			if (tLiveUser==null) {
				resultMap.put("code", "0");
				resultMap.put("msg", "不存在账户"+username);
			}else {
				if (password.equals(tLiveUser.getPassword())) {//校验登录信息
					HttpSession session = request.getSession();
					session.setAttribute(SESSION_USER_KEY, tLiveUser);
					session.setMaxInactiveInterval(300*60);//10分钟过时

					resultMap.put("code", "1");

				}else {
					resultMap.put("code", "0");
					resultMap.put("msg", "密码错误");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("code", "0");
			resultMap.put("msg", "登陆失败"+e.getMessage());
		}
		String result = JSON.toJSONString(resultMap);
		return result;
	}
	//登出
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session!=null) {
			TLiveUser tLiveUser = (TLiveUser)session.getAttribute(SESSION_USER_KEY);
			session.invalidate();

		}
		return "/login";
	}
	//跳转到修改密码页
	@RequestMapping("/password")
	public String password(HttpServletRequest request){
		return "/TLiveUser/password";
	}
	//修改密码
	@RequestMapping("/modifypass")
	@ResponseBody
	public String modifypass(HttpServletRequest request){
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			HttpSession session = request.getSession();
			if (session!=null) {
				TLiveUser tLiveUser = (TLiveUser)session.getAttribute(SESSION_USER_KEY);
				String oldPwd = request.getParameter("oldPwd");//旧密码
				String newPwd = request.getParameter("newPwd");//新密码
				String rePwd = request.getParameter("rePwd");//重新输入新密码
				oldPwd = MD5Util.encode2MD5(oldPwd);
				if (tLiveUser.getPassword().equals(oldPwd)) {
					if (newPwd.equals(rePwd)) {
						tLiveUser.setPassword(MD5Util.encode2MD5(newPwd));
						tLiveUserService.updateUser(tLiveUser);
						resultMap.put("code", "1");
						resultMap.put("msg", "密码修改成功");
					}else {

						resultMap.put("code", "0");
						resultMap.put("msg", "新密码与第二次输入密码不同");
					}
				}else {
					resultMap.put("code", "0");
					resultMap.put("msg", "旧密码有误");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "0");
			resultMap.put("msg", "密码修改失败"+e.getMessage());
		}
		String result = JSON.toJSONString(resultMap);
		return result;
	}
	//跳转到修改用户信息页
	@RequestMapping("/editUser")
	public String editUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		TLiveUser tLiveUser = (TLiveUser)session.getAttribute(SESSION_USER_KEY);
		request.setAttribute("user", tLiveUser);
		return "/TLiveUser/edit";
	}
	//修改用户信息
	@RequestMapping("/saveUser")
	@ResponseBody
	public String saveUser(HttpServletRequest request){
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			String username = request.getParameter("username");
			String nickname = request.getParameter("nickname");
			String id = request.getParameter("id");
			int userId = Integer.parseInt(id);
			TLiveUser tLiveUser = tLiveUserService.queryById(userId);
			tLiveUser.setUsername(username);
			tLiveUser.setNickname(nickname);
			tLiveUserService.updateUser(tLiveUser);
			resultMap.put("code", "1");
			resultMap.put("msg", "用户信息修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "0");
			resultMap.put("msg", "用户信息修改失败"+e.getMessage());
		}
		String result = JSON.toJSONString(resultMap);
		return result;
	}
}
