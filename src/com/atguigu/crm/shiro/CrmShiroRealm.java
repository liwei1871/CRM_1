package com.atguigu.crm.shiro;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.UserService;

@Service("crmShiroRealm")
public class CrmShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	
	/**
	 * 当访问受保护的资源时, shiro 会调用 doGetAuthorizationInfo 方法.
	 * 可以从 PrincipalCollection 类型的参数中来获取当前登陆用户的信息.
	 */
	//进行授权的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		//获取到当前登录信息
		User user = (User)principals.getPrimaryPrincipal();
		
		//获取当前用户所拥有的权限
		List<Authority> authorities = user.getRole().getAuthorities();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Authority authority : authorities) {
			info.addRole(authority.getName());//授权
			System.out.println(authority.getName());
		}
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String userName = upToken.getUsername();

		User user = userService.getUserByName(userName);

		if (user == null) {
			throw new UnknownAccountException("该用户不存在");
		}

		if (user.getEnabled() != 1) {
			throw new LockedAccountException("该用户被锁定");
		}

		Object principal = user;
		Object hashedCredentials = user.getPassword();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		String realmName = getName();

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
				hashedCredentials, credentialsSalt, realmName);

		return info;
		
	}

	// @PostConstruct相当于配置文件中的init-method
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);

		setCredentialsMatcher(credentialsMatcher);
	}
	
	
	public static void main(String[] args) {
		String algorithmName = "MD5";
		//Object source = 
		
		//Object result = new SimpleHash(algorithmName, source, salt, hashIterations)
	}
	

}
