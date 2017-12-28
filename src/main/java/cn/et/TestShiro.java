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
		//��ini�ж�ȡȨ����Ϣ  ����SecurityManager����   ͨ������ģʽ����
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:my.ini");
		//��ͨ��get��ֵ
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//��SecurityManager�赽SecurityUtils��
		SecurityUtils.setSecurityManager(securityManager);
		
		//��ȡ��ǰִ�е��û�
		Subject currentUser = SecurityUtils.getSubject();
		
		//��ǰ�û��ĻỰ
		Session session = currentUser.getSession();
		
		//�жϵ�ǰ�û��Ƿ��¼  δ��¼����Ҫ��¼
		if ( !currentUser.isAuthenticated() ) {
			//�û�������û���������
			UsernamePasswordToken token = new UsernamePasswordToken("jiaozi","123456");
			//���˺ź�������Ϣ���õ�cookie 
			token.setRememberMe(true);
			
			try {
			    currentUser.login( token );
			    System.out.println(currentUser.isAuthenticated());
			    System.out.println("��¼�ɹ�");
			    
			    //����¼����û��Ƿ�ӵ��ĳ����ɫ
			    //����û������ڵ�¼�Ժ���ܼ���û��Ƿ�ӵ��ĳ����ɫ
			    //��Ȩ��������֤֮��
			    if(currentUser.hasRole("role1")) {
			    	System.out.println("�Ƿ�ӵ��role1��Ȩ��");
			    }
			    if(currentUser.isPermitted("user:query:1")) {
			    	System.out.println("ӵ�в鿴1�ŵ�Ȩ��");
			    }
			    
			    if(currentUser.isPermitted("user:delete:2")) {
			    	System.out.println("�Ƿ�ӵ��ɾ��2�ŵ�Ȩ��");
			    }
			    
			} catch ( UnknownAccountException uae ) {
				System.out.println("�˺Ŵ���");
			} catch ( IncorrectCredentialsException ice ) {
				System.out.println("���벻ƥ��");
			} catch ( LockedAccountException lae ) {
				System.out.println("�˺ű�����");
			} catch ( AuthenticationException ae ) {
				System.out.println("δ֪�쳣");
			}
		}
	}

}
