/*package com.ccl.base.config.security.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;


*//**
 * 
 * @ClassName：MyConcurrentSessionFilter
 * @Description：自定义session过滤器
 * @Author：xiedong
 * @Date：2017年12月27日下午2:40:31
 * @version：1.0.0
 *//*
public class MyConcurrentSessionFilter extends GenericFilterBean  {

	// ~ Instance fields
	// ================================================================================================

	private final SessionRegistry sessionRegistry;
	private String expiredUrl;
	private RedirectStrategy redirectStrategy;
	private LogoutHandler handlers = new CompositeLogoutHandler(new SecurityContextLogoutHandler());
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

	// ~ Methods
	// ========================================================================================================

	public MyConcurrentSessionFilter(SessionRegistry sessionRegistry) {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		this.sessionRegistry = sessionRegistry;
		this.sessionInformationExpiredStrategy = new ResponseBodySessionInformationExpiredStrategy();
	}

	*//**
	 * Creates a new instance
	 *
	 * @param sessionRegistry the SessionRegistry to use
	 * @param expiredUrl the URL to redirect to
	 * @deprecated use {@link #MyConcurrentSessionFilter(SessionRegistry, SessionInformationExpiredStrategy)} with {@link SimpleRedirectSessionInformationExpiredStrategy} instead.
	 *//*
	@Deprecated
	public MyConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		Assert.isTrue(expiredUrl == null || UrlUtils.isValidRedirectUrl(expiredUrl),
				expiredUrl + " isn't a valid redirect URL");
		this.expiredUrl = expiredUrl;
		this.sessionRegistry = sessionRegistry;
		this.sessionInformationExpiredStrategy = new SessionInformationExpiredStrategy() {

			@Override
			public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
				HttpServletRequest request = event.getRequest();
				HttpServletResponse response = event.getResponse();
				SessionInformation info = event.getSessionInformation();

				redirectStrategy.sendRedirect(request, response, determineExpiredUrl(request, info));
			}

		};
	}

	public MyConcurrentSessionFilter(SessionRegistry sessionRegistry, SessionInformationExpiredStrategy sessionInformationExpiredStrategy) {
		Assert.notNull(sessionRegistry, "sessionRegistry required");
		Assert.notNull(sessionInformationExpiredStrategy, "sessionInformationExpiredStrategy cannot be null");
		this.sessionRegistry = sessionRegistry;
		this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(false);

		if (session != null) {
			SessionInformation info = sessionRegistry.getSessionInformation(session
					.getId());

			if (info != null) {
				if (info.isExpired()) {
					// Expired - abort processing
					if (logger.isDebugEnabled()) {
						logger.debug("Requested session ID "
								+ request.getRequestedSessionId() + " has expired.");
					}
					doLogout(request, response);
					
					this.sessionInformationExpiredStrategy.onExpiredSessionDetected(new SessionInformationExpiredEvent(info, request, response));
					//踢出登录删除session
					sessionRegistry.removeSessionInformation(info.getSessionId());
					return;
				}
				else {
					// Non-expired - update last request date/time
					sessionRegistry.refreshLastRequest(info.getSessionId());
				}
			}
		}

		chain.doFilter(request, response);
	}

	*//**
	 * Determine the URL for expiration
	 * @param request the HttpServletRequest
	 * @param info the {@link SessionInformation}
	 * @return the URL for expiration
	 * @deprecated Use {@link #MyConcurrentSessionFilter(SessionRegistry, SessionInformationExpiredStrategy)} instead.
	 *//*
	protected String determineExpiredUrl(HttpServletRequest request,
			SessionInformation info) {
		return expiredUrl;
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		this.handlers.logout(request, response, auth);
	}

	public void setLogoutHandlers(LogoutHandler[] handlers) {
		this.handlers = new CompositeLogoutHandler(handlers);
	}

	*//**
	 * Sets the {@link RedirectStrategy} used with {@link #MyConcurrentSessionFilter(SessionRegistry, String)}
	 * @param redirectStrategy the {@link RedirectStrategy} to use
	 * @deprecated use {@link #MyConcurrentSessionFilter(SessionRegistry, SessionInformationExpiredStrategy)} instead.
	 *//*
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	*//**
	 * A {@link SessionInformationExpiredStrategy} that writes an error message to the response body.
	 * @author Rob Winch
	 * @since 4.2
	 *//*
	private static final class ResponseBodySessionInformationExpiredStrategy
			implements SessionInformationExpiredStrategy {
		@Override
		public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
				throws IOException, ServletException {
			HttpServletResponse response = event.getResponse();
			response.getWriter().print(
					"This session has been expired (possibly due to multiple concurrent "
							+ "logins being attempted as the same user).");
			response.flushBuffer();
		}
	}
}
*/