package com.ccl.base.config.security.session;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;

public class MyConcurrentSessionControlAuthenticationStrategy implements
MessageSourceAware, SessionAuthenticationStrategy  {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final SessionRegistry sessionRegistry;
	private boolean exceptionIfMaximumExceeded = false;
	private int maximumSessions = 1;

	public MyConcurrentSessionControlAuthenticationStrategy(SessionRegistry mySessionRegistryImpl) {
		Assert.notNull(mySessionRegistryImpl, "The sessionRegistry cannot be null");
		this.sessionRegistry = mySessionRegistryImpl;
	}

	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		List<SessionInformation> sessions = this.sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
		int sessionCount = sessions.size();
		int allowedSessions = this.getMaximumSessionsForThisUser(authentication);
		if (sessionCount >= allowedSessions) {
			if (allowedSessions != -1) {
				if (sessionCount == allowedSessions) {
					HttpSession session = request.getSession(false);
					if (session != null) {
						Iterator<SessionInformation> var8 = sessions.iterator();

						while (var8.hasNext()) {
							SessionInformation si = (SessionInformation) var8.next();
							if (si.getSessionId().equals(session.getId())) {
								return;
							}
						}
					}
				}

				this.allowableSessionsExceeded(sessions, allowedSessions, this.sessionRegistry);
			}
		}
	}

	protected int getMaximumSessionsForThisUser(Authentication authentication) {
		return this.maximumSessions;
	}

	protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions,
			SessionRegistry registry) throws SessionAuthenticationException {
		if (!this.exceptionIfMaximumExceeded && sessions != null) {
			SessionInformation leastRecentlyUsed = null;
			Iterator<SessionInformation> var5 = sessions.iterator();

			while (true) {
				SessionInformation session;
				do {
					if (!var5.hasNext()) {
						leastRecentlyUsed.expireNow();
						((MySessionRegistryImpl) sessionRegistry).addSessionInfo(leastRecentlyUsed.getSessionId(),leastRecentlyUsed);
						return;
					}

					session = (SessionInformation) var5.next();
				} while (leastRecentlyUsed != null && !session.getLastRequest().before(leastRecentlyUsed.getLastRequest()));

				leastRecentlyUsed = session;
			}
		} else {
			throw new SessionAuthenticationException(
					this.messages.getMessage("ConcurrentSessionControlAuthenticationStrategy.exceededAllowed",
							new Object[] { Integer.valueOf(allowableSessions) },
							"Maximum sessions of {0} for this principal exceeded"));
		}
	}

	public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
		this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
	}

	public void setMaximumSessions(int maximumSessions) {
		Assert.isTrue(maximumSessions != 0,
				"MaximumLogins must be either -1 to allow unlimited logins, or a positive integer to specify a maximum");
		this.maximumSessions = maximumSessions;
	}

	public void setMessageSource(MessageSource messageSource) {
		Assert.notNull(messageSource, "messageSource cannot be null");
		this.messages = new MessageSourceAccessor(messageSource);
	}

}