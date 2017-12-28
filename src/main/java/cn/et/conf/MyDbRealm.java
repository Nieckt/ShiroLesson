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
	 * ��֤
	 * ����½������û��������������ݿ��е��û���������Ա�  �Ƿ����
	 * ����ֵ��null��ʾ��֤ʧ��  ��null��֤ͨ��
	 * 
	 * �ڵ�½��ʱ����Զ����ô˷���
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//ҳ�洫�������û���������
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(upt.getUsername());
		System.out.println(upt.getPassword().equals(new String(queryUser.getPassword())));
		//queryUser.getPassword()���ݿ������� �� upt.getPassword()ҳ�洫������
		if(queryUser != null && queryUser.getPassword().equals(upt.getPassword())) {
			//�������SimpleAccount�ͱ�ʾ��½�ɹ�
			SimpleAccount sa = new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
		}
		//������ؿվͱ�ʾ��½ʧ��,�����½ʧ�ܾ�����ʧ�ܵ�ҳ��
		return null;
	}
	
	/**
	 * ��ȡ��ǰ�û�����Ȩ��Ϣ
	 * ����ǰ�û������ݿ�Ľ�ɫ��Ȩ�� ���ص�AuthorizationInfo ����������������洢����Ȩ����Ϣ(getRoles��getStringPermissions)
	 * Ĭ���ڽ�����Ȩ��֤ʱ����
	 * ���Ȩ�޵��� checkRole  checkPerm
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = principals.getPrimaryPrincipal().toString();
		Set<String> roleSet = userMapper.queryRoleByName(userName);
		Set<String> permsSet = userMapper.queryPermsByName(userName);
		//��ɫ��Ȩ�޼��϶���
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(permsSet);
		return authorizationInfo;
	}

}
     