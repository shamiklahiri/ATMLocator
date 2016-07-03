package com.backbase.atmlocator.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RestServiceAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final String AUTHORISED_USER = "backbase";
	private static final String AUTHORISED_PWD = "backbase";
	private static final String AUTHORISED_ROLE = "searchATM";
	
	private static final String AUTHENTICATION_KEY_USER = "ATM_LOCATOR_USER";
	private static final String AUTHENTICATION_KEY_PWD = "ATM_LOCATOR_PWD";
	private static final String AUTHENTICATION_KEY_ROLE = "ATM_LOCATOR_ROLE";
	

	public RestServiceAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authenticationManager);
		setAuthenticationSuccessHandler(successHandler);
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String user = request.getHeader(AUTHENTICATION_KEY_USER);
		String pwd = request.getHeader(AUTHENTICATION_KEY_PWD);
		String role = request.getHeader(AUTHENTICATION_KEY_ROLE);
		
		boolean userOK = false;
		boolean pwdOK = false;
		boolean roleOK = false;
		if (null != user && AUTHORISED_USER.equalsIgnoreCase(user)){
			userOK = true;
		}
		if (null != pwd && AUTHORISED_PWD.equalsIgnoreCase(pwd)){
			pwdOK = true;
		}
		if (null != role && AUTHORISED_ROLE.equalsIgnoreCase(role)){
			roleOK = true;
		}
		AbstractAuthenticationToken authentication = null;
		if (userOK && pwdOK && roleOK){
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority(role));
			User principal = new User(user, pwd, authorities);
			authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
		} else {
			throw new AuthenticationServiceException("Unauthorized Acccess Request, Not Allowed");
		}
		return authentication;
	}
}
