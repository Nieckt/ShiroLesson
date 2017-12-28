package cn.et.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HttpServletBean;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServletBean {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		try{
			currentUser.login(token);
			request.getRequestDispatcher("/suc.jsp").forward(request, response);
		} catch ( UnknownAccountException uae ) {
			System.out.println("’À∫≈¥ÌŒÛ");
		} catch ( IncorrectCredentialsException ice ) {
			System.out.println("√‹¬Î≤ª∆•≈‰");
		} catch ( LockedAccountException lae ) {
			System.out.println("’À∫≈±ªÀ¯∂®");
		} catch ( AuthenticationException ae ) {
			System.out.println("Œ¥÷™“Ï≥£");
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
