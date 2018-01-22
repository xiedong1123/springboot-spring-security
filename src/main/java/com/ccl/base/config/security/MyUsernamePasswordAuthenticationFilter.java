package com.ccl.base.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.ccl.base.utils.Const;
import com.ccl.base.utils.crypt.RSAUtils;


/**
 * 
 * @ClassName：MyUsernamePasswordAuthenticationFilter
 * @Description：登录验证过滤器  
 * @Author：xiedong
 * @Date：2017年12月19日上午9:42:22
 * @version：1.0.0
 */
public class MyUsernamePasswordAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	
	private SessionRegistry sessionRegistry;
	
	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	// ~ Static fields/initializers
	// =====================================================================================

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;


	// ~ Constructors
	// ===================================================================================================

	public MyUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}
	
	// ~ Methods
	// ========================================================================================================

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		//获取session中的秘钥
		String privateKey = (String)request.getSession().getAttribute(Const.PRIVATEKEY_IN_SESSION);
		//编码后的密码进行解码
		String newPassword = RSAUtils.getInstance().decryptByPrivateKey(password, privateKey);
		password = StringUtils.isNotBlank(newPassword) ? newPassword : "";
		//添加验证码验证
		String validationCode = obtainCaptcha(request);
		//获取session中的验证码
		String code = (String)request.getSession().getAttribute(Const.VALIDATECODE_IN_SESSION);
        if(validationCode == null || !validationCode.toString().equals(code)) {
        	throw new ValidationCodeException("验证码错误");
        }
        request.getSession().removeAttribute(Const.VALIDATECODE_IN_SESSION);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		MyAuthenticationToken authRequest = new MyAuthenticationToken(
				username, password,validationCode);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
		sessionRegistry.registerNewSession(request.getSession().getId(), authenticate.getPrincipal());
		return authenticate;
	}

	/**
	 * Enables subclasses to override the composition of the password, such as by
	 * including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in addition to
	 * the password. A delimiter such as a pipe (|) should be used to separate the
	 * password and extended value(s). The <code>AuthenticationDao</code> will need to
	 * generate the expected password in a corresponding manner.
	 * </p>
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the password that will be presented in the <code>Authentication</code>
	 * request token to the <code>AuthenticationManager</code>
	 */
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as by
	 * including additional values and a separator.
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the username that will be presented in the <code>Authentication</code>
	 * request token to the <code>AuthenticationManager</code>
	 */
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the authentication
	 * request's details property.
	 *
	 * @param request that an authentication request is being created for
	 * @param authRequest the authentication request object that should have its details
	 * set
	 */
	protected void setDetails(HttpServletRequest request,
			MyAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login
	 * request.
	 *
	 * @param usernameParameter the parameter name. Defaults to "username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the login
	 * request..
	 *
	 * @param passwordParameter the parameter name. Defaults to "password".
	 */
	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter. If set to
	 * true, and an authentication request is received which is not a POST request, an
	 * exception will be raised immediately and authentication will not be attempted. The
	 * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
	 * authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}
	
	protected String obtainCaptcha(HttpServletRequest request) {
		return request.getParameter("validate_code");
	}
}
