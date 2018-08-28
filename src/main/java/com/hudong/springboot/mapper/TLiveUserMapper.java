package com.hudong.springboot.mapper;


import com.hudong.springboot.bean.TLiveUser;
import com.hudong.springboot.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Dao层接口
 * @author Lenovo
 * 2018-08-03 11:08:10
 */
public interface TLiveUserMapper extends MyMapper<TLiveUser> {

	//添加用户
	public void addUser(TLiveUser tLiveUser);
	//修改用户
	public void updateUser(TLiveUser tLiveUser);	
	//删除用户
	public void deleteById(int id);
	//根据id查询用户
	public TLiveUser queryById(int id);
	//根据用户名查询用户
	public TLiveUser queryByName(String username);
	
}
