package cn.et.conf;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.et.dao.UserMapper;
import cn.et.entity.UserInfo;
@Component
public class MyDbRealm extends AuthorizingRealm {
	@Autowired
	UserMapper userMapper;
	/**
	 * 认证
	 * 将登陆输入的用户名和密码与数据库中的用户名和密码对比  是否相等
	 * 返回值是null表示认证失败  非null认证通过
	 * 
	 * 在登陆的时候会自动调用此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//页面传过来的用户名和密码
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(upt.getUsername());
		System.out.println(upt.getPassword().equals(new String(queryUser.getPassword())));
		//queryUser.getPassword()数据库查出来的 和 upt.getPassword()页面传过来的
		if(queryUser != null && queryUser.getPassword().equals(upt.getPassword())) {
			//如果返回SimpleAccount就表示登陆成功
			SimpleAccount sa = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
		}
		//如果返回空就表示登陆失败,如果登陆失败就跳到失败的页面
		return null;
	}
	
	/**
	 * 获取当前用户的授权信息
	 * 将当前用户在数据库的角色和权限 加载到AuthorizationInfo 在里面有两个数组存储他的权限信息(getRoles和getStringPermissions)
	 * 默认在进行授权认证时调用
	 * 检查权限调用 checkRole  checkPerm
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = principals.getPrimaryPrincipal().toString();
		Set<String> roleSet = userMapper.queryRoleByName(userName);
		Set<String> permsSet = userMapper.queryPermsByName(userName);
		//角色和权限集合对象
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(permsSet);
		return authorizationInfo;
	}

}
     