package com.hudong.springboot.service;

import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.hudong.springboot.bean.TLiveInfo;
import com.hudong.springboot.mapper.TLiveInfoMapper;
import com.hudong.springboot.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * dao实现层
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
@Service
public class TLiveInfoService {


	private  String preyKey="b16live.";
	private  String hotKey="b16live.hotLive.id";//存放热点数据:首页直播
	private  String hotListKey="b16live.hotLive.list";//存放热点数据:直播列表
	private  String hotListTotalKey="b16live.hotLive.list.total";//直播总数

	@Autowired
	private TLiveInfoMapper tLiveInfoMapper;

	public int queryMaxId(){
		return tLiveInfoMapper.queryMaxId();
	}
	public int addLive(TLiveInfo tLiveInfo){
		return tLiveInfoMapper.addLive(tLiveInfo);
	}
	public List<TLiveInfo> getAdminList(TLiveInfo tLiveInfo,int pageSize,int pageNo){
		return tLiveInfoMapper.getAdminList(tLiveInfo);
	}

	public List<TLiveInfo> appLiveIndex(TLiveInfo tLiveInfo){
		return tLiveInfoMapper.appLiveIndex(tLiveInfo);
	}

	/**
	 * 分页
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean<TLiveInfo>  getAdminListPageV2(Map<String,String> map, int pageNo, int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		List<TLiveInfo> list=tLiveInfoMapper.getAdminListPage(map);
		//int total=tLiveInfoMapper.getAdminListPageSize(map);
		PageInfo<TLiveInfo> pageInfo=new PageInfo<TLiveInfo>(list);//
		Long total=pageInfo.getTotal();
		PageBean<TLiveInfo> pageData = new PageBean<>(pageNo,pageSize,total);
		pageData.setItems(list);
		return pageData;
	}
	/**
	 * 获取直播总数
	 * @param map
	 * @return
	 */
	public int getAdminListPageSize(Map<String,String> map){
		return tLiveInfoMapper.getAdminListPageSize(map);
	}

	public void updateBeginOrEnd(TLiveInfo tLiveInfo){
		tLiveInfoMapper.updateBeginOrEnd(tLiveInfo);
	}
	public void deleteLive(int id){
		tLiveInfoMapper.deleteLive(id);
	}

	public List<TLiveInfo> findStartLiveList() {
		return  tLiveInfoMapper.findStartLiveList();
	}

	public List<TLiveInfo> getAllLive() {
		return tLiveInfoMapper.getAllLive();
	}

	public TLiveInfo getLiveInfoById(int liveId) {
		return tLiveInfoMapper.getLiveInfoById(liveId);
	}

	/**
	 * 根据直播频道id查找直播id
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 17:53 2018/8/7
	*/
	public String getIdByChannelId(String channelId){
		List<Map<String,Object>> list=tLiveInfoMapper.getIdByChannelId(channelId);
		if(list!=null&&list.size()>0){
			Map<String,Object> thisMap=list.get(0);
			return thisMap.get("ID").toString();
		}
		return "";
	}
	/**
     * 获得直播对象
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 20:12 2018/8/7
	*/
    public TLiveInfo get(String id){
	    return tLiveInfoMapper.get(id);
    }

    /**
	 * 根据推流地址生成频道id
    *@Author: 90
    *@Description
    *@Param  * @param null
    *@Date 10:09 2018/8/8
    */
	public String getChannelIdFromObs(String obsUrl) {
		String channelId = "";
		int a = obsUrl.lastIndexOf("live/");
		int b = obsUrl.indexOf("?bizid");
		int c = obsUrl.indexOf(".flv");
		int d = obsUrl.indexOf(".m3u8");
		if (a==-1) {
			return "";
		}else if (b!=-1) {
			channelId = obsUrl.substring(a+5, b);
		}else if (c!=-1) {
			channelId = obsUrl.substring(a+5, c);
		}else if (d!=-1) {
			channelId = obsUrl.substring(a+5, d);
		}else {
			channelId = obsUrl.substring(a+5);
		}
		return channelId;
	}

	/**
	 * app端获取直播的更多，分页解析
	*@Author: 90
	*@Description
	*@Param  * @param null
	*@Date 10:26 2018/8/8
	*/
	public PageBean<TLiveInfo>  getAppLiveList(Map<String,String> map, int pageNo, int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		List<TLiveInfo> list=tLiveInfoMapper.getAppLiveList(map);
		int total=tLiveInfoMapper.getAppLiveListSize(map);
		PageBean<TLiveInfo> pageData = new PageBean<>(pageNo,pageSize,Long.valueOf(total+""));
		pageData.setItems(list);
		return pageData;
	}

	//添加事务支持
	//@Transactional(rollbackFor=Exception.class)
	//@Transactional(noRollbackFor=Exception.class)//指定不回滚
	@Transactional (propagation = Propagation.REQUIRED,timeout=30)//超时
	public void updateLive(TLiveInfo tLiveInfo){


		tLiveInfoMapper.updateLive(tLiveInfo);
		Integer.valueOf(tLiveInfo.getTitle());
		tLiveInfo.setTitle("111111");
		tLiveInfoMapper.updateLive(tLiveInfo);

	}

}
