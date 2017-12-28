package cn.et.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;

import cn.et.entity.Menu;
import cn.et.entity.UserInfo;

public interface UserMapper {
	@Select("select user_name as userName, pass_word as password from user_info where user_name=#{0}")
	public UserInfo queryUser(String userName);
	
	/**
	 * 查权限名
	 * set是无重复元素的集合
	 * @param userName
	 * @return
	 */
	@Select(" SELECT r.role_name FROM user_info u INNER JOIN user_role_relation urr ON u.user_id = urr.user_id "
			+ " INNER JOIN role r ON urr.role_id = r.role_id"
			+ " WHERE user_name = #{0}")
	public Set<String> queryRoleByName(String userName);
	
	@Select(" SELECT p.perm_tag FROM user_info u INNER JOIN user_role_relation urr ON u.user_id=urr.user_id"
			   +" INNER JOIN role r ON urr.role_id=r.role_id"
			   +" INNER JOIN role_perms_relation rpr ON rpr.role_id=r.role_id"
			   +" INNER JOIN perms p ON p.perm_id=rpr.perm_id"
			   +" WHERE user_name = #{0}")
	public Set<String> queryPermsByName(String userName);
	
	@Select("select menu_id as menuid,menu_name as menuname,menu_url as menuurl,menu_filter as menufilter from menu")
	public List<Menu> queryMenu();
	
	@Select("select menu_id as menuid,menu_name as menuname,menu_url as menuurl,menu_filter as menufilter from menu where user_url=#{0}")
	public List<Menu> queryMenuByUrl(String url);
}
