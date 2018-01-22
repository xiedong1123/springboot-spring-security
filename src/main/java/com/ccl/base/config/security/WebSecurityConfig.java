package com.ccl.base.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsUtils;

import com.ccl.base.config.security.extra.RedisUserCache;
import com.ccl.base.config.security.md5.AnnotationSaltSource;
import com.ccl.base.config.security.md5.Md5PasswordEncoder;
import com.ccl.base.config.security.session.MyConcurrentSessionControlAuthenticationStrategy;
import com.ccl.base.config.security.session.MySessionRegistryImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//登录请求地址
	private final static String loginProcessingUrl = "/login";
	//登录失败 or session失效 跳转地址
	private final static String failureUrl = "/";
	//登录成功跳转地址
	private final static String defaultSuccessUrl = "/main";
	private final static Boolean alwaysUse = true;
	//设置同一个账号登录上线
	private final static int maximumSessions = 1;
	
	/*@Bean
	public AccessDecisionManager accessDecisionManager() {
		return new CustomAccessDecisionManager();
	}

	@Bean
	public CustomInvocationSecurityMetadataSource securityMetadataSource() {
		return new CustomInvocationSecurityMetadataSource();
	}*/
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**", "/popup/**","/app/**");
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 解决不允许显示在iframe的问题
		http.headers().frameOptions().disable();
		http.csrf().disable();
		http.authorizeRequests().mvcMatchers("/authImage/**","/getPublicKey/**","/validateCode/**").permitAll().anyRequest().authenticated()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.and().formLogin().loginPage("/")
				.permitAll().and().logout().logoutSuccessUrl("/").addLogoutHandler(mySecurityContextLogoutHandler()).permitAll();
		http.addFilterBefore(myFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
		//添加自定义登录验证过滤器在默认的之前
		http.addFilterBefore(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		super.configure(http);
	}
	
	/**
	 * 
	 * @MethodName：myConcurrentSessionFilter
	 * @return
	 * @throws Exception
	 * @ReturnType：MyConcurrentSessionFilter
	 * @Description：session 控制过滤器
	 * @Creator：xiedong
	 * @CreateTime：2017年12月27日下午2:33:20
	 * @Modifier：
	 * @ModifyTime：
	 */
	@Bean
	public ConcurrentSessionFilter myConcurrentSessionFilter() {
		//设置session失效跳转地址
		SimpleRedirectSessionInformationExpiredStrategy sessionInformationExpiredStrategy = new SimpleRedirectSessionInformationExpiredStrategy(failureUrl);
		ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(mySessionRegistryImpl(),sessionInformationExpiredStrategy);
		//设置自定义退出登录handler
		LogoutHandler[] handlers = new LogoutHandler[]{mySecurityContextLogoutHandler()};
		concurrentSessionFilter.setLogoutHandlers(handlers);
		return concurrentSessionFilter;
	}
    
	@Bean
	public MySessionRegistryImpl mySessionRegistryImpl() {
		return new MySessionRegistryImpl();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public MessageDigestPasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}

	@Bean
	public SaltSource saltSource() {
		return new AnnotationSaltSource();
	}

	@Bean
	public RedisUserCache userCache() {
		return new RedisUserCache();
	}


	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(myAuthenticationProvider());
		//super.configure(auth);

	}

	@Bean
	public MyAuthenticationProvider myAuthenticationProvider() {
		MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
		return myAuthenticationProvider;
	}*/

	@Autowired
	private AuthenticationManager authManager;
	

	@Bean
	public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
		MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
		myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(simpleUrlAuthenticationSuccessHandler());
		myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
		myUsernamePasswordAuthenticationFilter.setFilterProcessesUrl(loginProcessingUrl);
		myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authManager);
		myUsernamePasswordAuthenticationFilter.setSessionRegistry(mySessionRegistryImpl());
		MyConcurrentSessionControlAuthenticationStrategy myConcurrentSessionControlAuthenticationStrategy = new MyConcurrentSessionControlAuthenticationStrategy(mySessionRegistryImpl());
		//设置 一个账号最大登录人数
		myConcurrentSessionControlAuthenticationStrategy.setMaximumSessions(maximumSessions);
		myUsernamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(myConcurrentSessionControlAuthenticationStrategy);
		return myUsernamePasswordAuthenticationFilter;
	}
	
	
	/**
	 * 
	 * @MethodName：simpleUrlAuthenticationSuccessHandler
	 * @return
	 * @ReturnType：SimpleUrlAuthenticationSuccessHandler
	 * @Description：登陆成功跳转地址
	 * @Creator：xiedong
	 * @CreateTime：2017年12月18日上午9:33:00
	 * @Modifier：
	 * @ModifyTime：
	 */
	protected SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler(){
		SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
		simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl(defaultSuccessUrl);
		simpleUrlAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(alwaysUse);
		return simpleUrlAuthenticationSuccessHandler;
		
	}
	
	@Bean
	public MyFilterSecurityInterceptor myFilterSecurityInterceptor() throws Exception {
		MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
		return myFilterSecurityInterceptor;
	}
	
	/**
	 * 
	 * @MethodName：mySecurityContextLogoutHandler
	 * @return
	 * @ReturnType：LogoutHandler
	 * @Description：退出登录设置
	 * @Creator：xiedong
	 * @CreateTime：2017年12月27日下午3:12:00
	 * @Modifier：
	 * @ModifyTime：
	 */
	public LogoutHandler mySecurityContextLogoutHandler(){
		return new LogoutHandler() {
			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				SecurityContext context = SecurityContextHolder.getContext();
				context.setAuthentication(null);
				SecurityContextHolder.clearContext();
				//删除redis中的session
				mySessionRegistryImpl().removeSessionInformation(session.getId());
				
			}
		};
	}
}