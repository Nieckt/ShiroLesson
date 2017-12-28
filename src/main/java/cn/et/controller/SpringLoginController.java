package cn.et.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringLoginController {
	@RequestMapping("/loginShiro")
	public String login(String userName,String password) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		try {
			currentUser.login(token);
			return "/suc.jsp";
		} catch ( UnknownAccountException uae ) {
			System.out.println("�˺Ŵ���");
		} catch ( IncorrectCredentialsException ice ) {
			System.out.println("���벻ƥ��");
		} catch ( LockedAccountException lae ) {
			System.out.println("�˺ű�����");
		} catch ( AuthenticationException ae ) {
			System.out.println("δ֪�쳣");
		}
		return "/error.jsp";
	}
}
