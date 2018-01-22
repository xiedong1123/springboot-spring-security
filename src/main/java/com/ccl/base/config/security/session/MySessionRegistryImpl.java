package com.ccl.base.config.security.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.Assert;

public class MySessionRegistryImpl implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {


    private static final String SESSIONIDS = "sessionIds";

    private static final String PRINCIPALS = "principals";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    protected final Logger logger = LoggerFactory.getLogger(MySessionRegistryImpl.class);
//    private final ConcurrentMap<Object, Set<String>> principals = new ConcurrentHashMap();
//    private final Map<String, SessionInformation> sessionIds = new ConcurrentHashMap();

    @Override
    public List<Object> getAllPrincipals() {
        return new ArrayList<>(this.getPrincipalsKeySet());
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        Set<String> sessionsUsedByPrincipal =  this.getPrincipals(principal);
        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        } else {
            List<SessionInformation> list = new ArrayList<>(sessionsUsedByPrincipal.size());
            Iterator<String> var5 = sessionsUsedByPrincipal.iterator();

            while (true) {
                SessionInformation sessionInformation;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return list;
                        }

                        String sessionId = (String) var5.next();
                        sessionInformation = this.getSessionInformation(sessionId);
                    } while (sessionInformation == null);
                } while (!includeExpiredSessions && sessionInformation.isExpired());

                list.add(sessionInformation);
            }
        }
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        return (SessionInformation) this.getSessionInfo(sessionId);
    }
    
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        String sessionId = event.getId();
        this.removeSessionInformation(sessionId);
    }
    
    @Override
    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
        }

    }
    
    @Override
    public void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        Assert.notNull(principal, "Principal required as per interface contract");
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Registering session " + sessionId + ", for principal " + principal);
        }

        if (this.getSessionInformation(sessionId) != null) {
            this.removeSessionInformation(sessionId);
        }

        this.addSessionInfo(sessionId, new SessionInformation(principal, sessionId, new Date()));

//        this.sessionIds.put(sessionId, new SessionInformation(principal, sessionId, new Date()));
        Set<String> sessionsUsedByPrincipal = (Set<String>) this.getPrincipals(principal);
        if (sessionsUsedByPrincipal == null) {
            sessionsUsedByPrincipal = new CopyOnWriteArraySet<>();
            Set<String> prevSessionsUsedByPrincipal = (Set<String>) this.putIfAbsentPrincipals(principal, sessionsUsedByPrincipal);
            if (prevSessionsUsedByPrincipal != null) {
                sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
            }
        }

        ((Set<String>) sessionsUsedByPrincipal).add(sessionId);
        this.putPrincipals(principal, sessionsUsedByPrincipal);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Sessions used by '" + principal + "' : " + sessionsUsedByPrincipal);
        }

    }
    
    @Override
    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            if (this.logger.isTraceEnabled()) {
                this.logger.debug("Removing session " + sessionId + " from set of registered sessions");
            }

            this.removeSessionInfo(sessionId);
            Set<String> sessionsUsedByPrincipal = (Set<String>) this.getPrincipals(info.getPrincipal());
            if (sessionsUsedByPrincipal != null) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Removing session " + sessionId + " from principal's set of registered sessions");
                }

                sessionsUsedByPrincipal.remove(sessionId);
                if (sessionsUsedByPrincipal.isEmpty()) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Removing principal " + info.getPrincipal() + " from registry");
                    }

                    this.removePrincipal(info.getPrincipal());
                }

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Sessions used by '" + info.getPrincipal() + "' : " + sessionsUsedByPrincipal);
                }

            }
        }
    }

    public void addSessionInfo(final String sessionId, final SessionInformation sessionInformation) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
        hashOperations.put(sessionId, sessionInformation);
      //  hashOperations.expire(10, TimeUnit.DAYS);
    }
    
    public SessionInformation getSessionInfo(final String sessionId) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
        return hashOperations.get(sessionId);
    }

    public void removeSessionInfo(final String sessionId) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
        hashOperations.delete(sessionId);
    }

    public Set<String> putIfAbsentPrincipals(final Object key, final Set<String> set) {
        BoundHashOperations<String, Object, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        hashOperations.putIfAbsent(key, set);
        return hashOperations.get(key);
    }

    public void putPrincipals(final Object key, final Set<String> set) {
        BoundHashOperations<String, Object, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        hashOperations.put(key,set);
        //hashOperations.expire(10, TimeUnit.DAYS);
    }

    public Set<String> getPrincipals(final Object key) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        return hashOperations.get(key);
    }

    public Set<String> getPrincipalsKeySet() {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        return hashOperations.keys();
    }

    public void removePrincipal(final Object key) {
        BoundHashOperations<String, Object, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        hashOperations.delete(key);
    }


}