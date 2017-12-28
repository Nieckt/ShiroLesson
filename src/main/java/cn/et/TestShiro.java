package cn.et;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestShiro {

	public static void main(String[] args) {
		//从ini中读取权限信息  构建SecurityManager对象   通过工厂模式生成
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");
		//再通过get设值
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//把SecurityManager设到SecurityUtils中
		SecurityUtils.setSecurityManager(securityManager);
		
		//获取当前执行的用户
		Subject currentUser = SecurityUtils.getSubject();
		
		//当前用户的会话
		Session session = currentUser.getSession();
		
		//判断当前用户是否登录  未登录才需要登录
		if ( !currentUser.isAuthenticated() ) {
			//用户输入的用户名和密码
			UsernamePasswordToken token = new UsernamePasswordToken("jiaozi","123456");
			//把账号和密码信息设置到cookie 
			token.setRememberMe(true);
			
			try {
			    currentUser.login( token );
			    System.out.println(currentUser.isAuthenticated());
			    System.out.println("登录成功");
			    
			    //检查登录后的用户是否拥有某个角色
			    //检查用户必须在登录以后才能检查用户是否拥有某个角色
			    //授权必须在认证之后
			    if(currentUser.hasRole("role1")) {
			    	System.out.println("是否拥有role1的权限");
			    }
			    if(currentUser.isPermitted("user:query:1")) {
			    	System.out.println("拥有查看1号的权限");
			    }
			    
			    if(currentUser.isPermitted("user:delete:2")) {
			    	System.out.println("是否拥有删除2号的权限");
			    }
			    
			} catch ( UnknownAccountException uae ) {
				System.out.println("账号错误");
			} catch ( IncorrectCredentialsException ice ) {
				System.out.println("密码不匹配");
			} catch ( LockedAccountException lae ) {
				System.out.println("账号被锁定");
			} catch ( AuthenticationException ae ) {
				System.out.println("未知异常");
			}
		}
	}

}
