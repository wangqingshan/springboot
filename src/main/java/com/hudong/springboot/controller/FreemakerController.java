package com.hudong.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/freemaker")
public class FreemakerController {

	


	
	@GetMapping("hello")
	public String index(ModelMap modelMap){

        //return "fm/index";  //不用加后缀，在配置文件里面已经指定了后缀
        return "fm/index";
    }

    private Map<String,Object> map=new HashMap<String,Object>();
    @RequestMapping("/hell")
    public String index() {
        System.out.println(4423);
        return "Hello World";
    }


    /**
     * 测试restful协议，从路径中获取字段
     * 小写不是驼峰，小写字母或者下划线
     * @param cityId
     * @param userId
     * @return
     */
    @RequestMapping(path="/{city_id}/{user_id}",method = RequestMethod.GET)
    public Object findUser(@PathVariable("city_id") String cityId, @PathVariable("user_id") String userId){

        return cityId;
    }

    @GetMapping(path="/v1/size")
    public Object find(String age){

        return age;
    }

    @GetMapping(path="/v1/find2")
    public Object find2(@RequestParam(defaultValue = "beijing",name="froms",required = true) String from,String name){

        map.clear();
        map.put("from",from);
        map.put("addr",name);
        return map;
    }

    /**
     * bean 对象传参
     * 注意：1 http头为content-type 为application/json    2 使用body传输数据

     * @return
     */
    @RequestMapping(path="/v1/find2")
    public Object saveUser(@RequestBody Map map){

        return map;
    }

    /**
     * 获取http头信息
     * 鉴权用
     * @param accessToken
     * @param id
     * @return
     */
    @GetMapping(path="/v1/getHeader")
    public Object getHeader(@RequestHeader("access_token") String accessToken,String id){
        map.clear();
        map.put("accessToken",accessToken);//写到header里
        map.put("id",id);
        return map;
    }

    @GetMapping(path="/v1/req")
    public Object getReq(HttpServletRequest request){
        map.clear();
   /* map.put("accessToken",accessToken);//写到header里
    map.put("id",id);*/
        return map;
    }

	
}
