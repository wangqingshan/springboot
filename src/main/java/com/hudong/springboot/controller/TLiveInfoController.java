package com.hudong.springboot.controller;


import com.hudong.springboot.bean.TLiveInfo;
import com.hudong.springboot.service.RedisService;
import com.hudong.springboot.service.TLiveInfoService;
import com.hudong.springboot.utils.ExcelUtil;
import com.hudong.springboot.utils.PageBean;
import com.hudong.springboot.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;



/**
 *  控制层
 * @author Lenovo
 *
 */
@Slf4j
@Controller
@RequestMapping("/")
public class TLiveInfoController  extends BaseController {

   Logger logger= LoggerFactory.getLogger(TLiveInfoController.class);

	@Autowired
	private TLiveInfoService tLiveInfoService;

	@Autowired
	private RedisService redisService;

	@Value("${my.number.in.range}")
	private String myNumberInRange;
	/**
     * 新建直播
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:15 2018/8/13
	*/
	@RequestMapping("/v1/create")
	public String createLive(HttpServletRequest request){
		String id = request.getParameter("id");
		request.setAttribute("date", new Date());
		return "/TLiveInfo/create";
	}

    /**
     * 修改直播
     *@Author: 90
     *@Description
     *@Param
     *@Date 11:15 2018/8/13
     */
	@RequestMapping("/v1/edit")
	public String editLive(HttpServletRequest request){
		String id = request.getParameter("id");
		TLiveInfo liveInfo=tLiveInfoService.get(id);
		request.setAttribute("liveInfo",liveInfo);
		//request.setAttribute("startTime",DateUtil.parseStringFullDate(liveInfo.getStartTime()));
		return "/TLiveInfo/edit";
	}

	/**
     * 更新直播
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:16 2018/8/13
	*/
	@RequestMapping("/v1/update")
	@ResponseBody
	public String updateLive(HttpServletRequest request){
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String startTime=request.getParameter("startTime");
		String zsxs=request.getParameter("zsxs");
		String obsUrl=request.getParameter("obsUrl");
		String detail=request.getParameter("detail");
		String isHF=request.getParameter("isHF");
		String imgV=request.getParameter("imgV");
		String imgH=request.getParameter("imgH");
		TLiveInfo tLiveInfo=tLiveInfoService.get(id);
		tLiveInfo.setDetail(detail);
		tLiveInfo.setIsHf(Integer.valueOf(isHF));
		/*tLiveInfo.setCreTime(new Date());*/
		tLiveInfo.setTitle(title);
		tLiveInfo.setDelFlag(0);
		tLiveInfo.setObsId(obsUrl);
		//tLiveInfo.setStatus(2);
		if(null!=imgV&&!"".equals(imgV)){
			tLiveInfo.setImgUrlV(imgV);
		}else{
			tLiveInfo.setImgUrlV(this.domainUrl+"/images/vdefault.png");

		}
		if(null!=imgH&&!"".equals(imgH)){
			tLiveInfo.setImgUrlH(imgH);
		}else{
			tLiveInfo.setImgUrlH(this.domainUrl+"/images/hdefault.png");
		}
		tLiveInfo.setOnlineNumZsxs(Float.valueOf(zsxs));
		tLiveInfo.setOnlineNum(Integer.valueOf(zsxs));
		/*try {
			tLiveInfo.setStartTime(DateUtil.parseFullDate(startTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/

		tLiveInfo.setChannelId(tLiveInfoService.getChannelIdFromObs(obsUrl));
		tLiveInfoService.updateLive(tLiveInfo);
		return null;
	}


	/**
     * 后台主页
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:16 2018/8/13
	*/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request){
		return "/index";
	}


	/**
     * 直播保存
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:16 2018/8/13
	*/
	@RequestMapping("/v1/save")
	public String saveLive(HttpServletRequest request) throws ParseException {

		String title = request.getParameter("title");
		String startTime=request.getParameter("startTime");
		String zsxs=request.getParameter("zsxs");
		String obsUrl=request.getParameter("obsUrl");
		String detail=request.getParameter("detail");
		String isHF=request.getParameter("isHF");
		String imgV=request.getParameter("imgV");
		String imgH=request.getParameter("imgH");

		return null;
	}

	/**
     * 管理员列表查看
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:17 2018/8/13
	*/
	@RequestMapping("/v1/adminList")
	public String  adminList(HttpServletRequest request){
		logger.error("错误日志");
		redisService.set("key_new","中华共1和");
		System.out.println(redisService.get("key_new"));
		TLiveInfo tLiveInfo=new TLiveInfo();
		int pageNo=1;
		int pageSize=10;
		List<TLiveInfo> dataList=tLiveInfoService.getAdminList(tLiveInfo,pageSize,pageNo);

		request.setAttribute("dataList",dataList);

		return "/TLiveInfo/adminList";
	}

	@RequestMapping("/v1/adminListAjax")
	public String  adminListAjax(HttpServletRequest request)  {
		//根据用户传来的token判断用户
		/*String token="1212";
		BaseUser user=this.getBaseUser(token);*/

		System.out.println("测试自动部署=====================================");
		String title=request.getParameter("title");
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		String endTime=request.getParameter("endTime");
		String startTime=request.getParameter("startTime");
		String pageNoStr=request.getParameter("pageNo");
		int pageNo=1;
		if(null!=pageNoStr&&!"".equals(pageNoStr)){
			pageNo=Integer.valueOf(pageNoStr);
		}

		int pageSize=10;
		Map<String,String>map=new HashMap<String,String>();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("status",status);
		map.put("title",title);
		map.put("id",id);
		PageBean<TLiveInfo> pageData=tLiveInfoService.getAdminListPageV2(map,pageNo,pageSize);
		//List<TLiveInfo> dataList=tLiveInfoService.getAdminListPage(map,pageNo,pageSize);
		pageData.goToPage(pageNo);//跳转到当前页
		request.setAttribute("dataList",pageData.getItems());
		request.setAttribute("pageHtml",pageData.getPageCode());//分页样式
		request.setAttribute("total",pageData.getTotalNum());
		request.setAttribute("sid",request.getSession().getId());
		/*LiveThread thread=new LiveThread("key");
		Thread t1 = new Thread(thread, "T1");
		t1.start();*/
		if(!redisService.exists("keys")){
			redisService.set("keys",123);
			new Thread(new Runnable(){
				@Override
				public void run() {
					System.out.println("子线程run");
					for(int i = 0;i<4000;i++){

						try {
							System.out.println("当前在线人数："+myNumberInRange);
							try {
								WebSocketServer.sendInfo(""+(int)(1+Math.random()*1000),null);//websocket群发
							} catch (IOException e) {
								e.printStackTrace();
							}
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					redisService.remove("keys");
					System.out.println("子线程运行结束");
				}
			}).start();
		}

		return "/TLiveInfo/adminListAjax";
	}



	/**
	 * app端获取app的首页的直播
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 12:02 2018/8/6
	*/
	//@PostMapping(path = "/v1/indexLive")
	@RequestMapping("/v1/indexLive")
	@ResponseBody
	public Object indexLive(@RequestBody Map<String, String> params) throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		if(true){
			throw new Exception("错误");
		}

		return result;
	}




	/**
	 * app端获取直播的更多列表
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 10:24 2018/8/8
	*/
	@PostMapping(path = "/v1/liveList")
	@ResponseBody
	public Object liveList(@RequestBody Map<String, String> params){
		Map<String,Object> result=new HashMap<String,Object>();
		return result;
	}





	/**
     * 图片上传
	*@Author: 90
	*@Description
	*@Param
	*@Date 11:18 2018/8/13
	*/
	@PostMapping("/v1/uploadV2")
	@ResponseBody
	public Map<String,String> uploadImgV2(@RequestParam("imgFile") MultipartFile multipartFile, HttpServletRequest request)  {
		Map<String,String> rltMap=new HashMap<String,String>();
		/*
		rltMap.put("status","0");
		String uploadUrl=this.getUserInfoUrl()+"/live/";//"http://shiliucaijing.baike.com/api/v1/live/";
		String uploadId=request.getParameter("uploadId");
		String url=uploadUrl+uploadId;
		String root_fileName = multipartFile.getOriginalFilename();
		if(multipartFile.getSize()>0){

			String dayStr=DateUtil.fullDateToMinutes();
			Date date;
			try {
				date=DateUtil.parseFullDateToMinutes(dayStr);
				String timeStr=date.getTime()+"";
				try {
					String token=MD5Util.encode2MD5(uploadId)+"hdbk"+timeStr.substring(0,timeStr.length()-3);
					token=MD5Util.encode2MD5(token);

					try {
						String rlt=HttpFileUploadUtil.uploadIconPic(url,token,multipartFile.getBytes(),root_fileName);
						if(rlt!=null&&!rlt.equals("")){
							JSONObject jsonImg = JSONObject.parseObject(rlt);
							String status=jsonImg.getString("status");
							if("1".equals(status)){
								rltMap.put("status","1");//上传成功
								Map<String,String> thisMap=(Map<String,String>)jsonImg.get("result");
								rltMap.put("imgUrl",thisMap.get("url"));
								//rltMap.put("imgUrl","http://newsfeed.att.hudong.com/live/96efe8e2e87c13b0ca4baf3d5772e3f1.jpg");
							}

						}
					} catch (IOException e) {
						logger.info("上传图片失败=========================================");
						e.printStackTrace();
					}

				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}


			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			//throw new Exception("上传失败：上传文件不能为空");
		}*/
		return rltMap;
	}


	/**
	 * 导出excel功能
	 * @param request
	 * @return
	 */
	@RequestMapping("/v1/exportLive")
	public String  exportLive(HttpServletRequest request, HttpServletResponse response){
		String title=request.getParameter("title");
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		String endTime=request.getParameter("endTime");
		String startTime=request.getParameter("startTime");
		String pageNoStr=request.getParameter("pageNo");
		int pageNo=1;
		if(null!=pageNoStr&&!"".equals(pageNoStr)){
			pageNo=Integer.valueOf(pageNoStr);
		}
		int pageSize=1000000;
		Map<String,String>map=new HashMap<String,String>();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("status",status);
		map.put("title",title);
		map.put("id",id);
		PageBean<TLiveInfo> pageData=tLiveInfoService.getAdminListPageV2(map,pageNo,pageSize);
		List<TLiveInfo> dataList=pageData.getItems();
		try {
			String fileName="直播列表";
			BufferedOutputStream bos = null;
			String userAgent = request.getHeader("user-agent");
			if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0) {
				fileName = new String((fileName).getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8"); // 其他浏览器
			}
			try {
				response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");
				OutputStream out =response.getOutputStream();
				String[] headers={"直播ID","直播名称","直播开始时间","直播状态","可否回放","操作人","操作时间"};
				String[] columns=null;
				ExcelUtil.expoortExcelx("石榴财经直播",headers,columns,dataList,out);
			}finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						throw e;
					}
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 删除直播
	 *@Author: 90
	 *@Description
	 *@Param  * @param null
	 *@Date 19:39 2018/8/6
	 */
	@RequestMapping("/v1/deleteLive")
	@ResponseBody
	public String deleteLive(HttpServletRequest request){
		String id=request.getParameter("id");
		String rlt="0";//更新失败
		if(id!=null&&!"".equals(id)){
			TLiveInfo tLiveInfo=tLiveInfoService.get(id);
			tLiveInfoService.deleteLive(Integer.valueOf(id));


			rlt="1";
		}
		return rlt;
	}

}
