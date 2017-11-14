package com.insigma.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;

import com.insigma.redis.RedisManager;
import com.insigma.redis.SerializeUtil;



/**
 * 
 * ����jedis��shiro session��
 * @author administrator
 * 
 *
 */
public class JedisShiroSessionRepository implements ShiroSessionRepository {
 
	
	private Log log = LogFactory.getLog(JedisShiroSessionRepository.class);  
	
    /**
     * 
     * redis session key 
     * 
     * */
    private final String keyPrefix = "shiro_redis_session ";
 
    private RedisManager redisManager;
 
    @Override
    public void saveSession(Session session) {
    	redisManager.init();
        if (session == null || session.getId() == null) {
            System.out.println("session ���� session ID Ϊ��");
            log.debug("session ���� session ID Ϊ��");  
        }
        
        //������session���л�
        byte[] key = SerializeUtil.serialize(getRedisSessionKey(session.getId()));
        byte[] value = SerializeUtil.serialize(session);
 
        Long timeOut = session.getTimeout() / 1000;
        redisManager.set(key, value, Integer.parseInt(timeOut.toString()));
 
    }
 
    @Override
    public void deleteSession(Serializable sessionId) {
        redisManager.init();
        if (sessionId == null) {
            log.debug("sessionId Ϊ��");  
        }
        redisManager.del(SerializeUtil.serialize(getRedisSessionKey(sessionId)));
 
    }
 
    @Override
    public Session getSession(Serializable sessionId) {
        redisManager.init();
        if (null == sessionId) {
            log.debug("sessionId Ϊ��");  
            return null;
        }
        Session session = null;
        byte[] value = redisManager.get(SerializeUtil.serialize(getRedisSessionKey(sessionId)));
        if (null == value)
            return null;
        session = SerializeUtil.deserialize(value,Session.class);
        return session;
    }
 
    @Override
    public Collection<Session> getAllSessions() {
    	redisManager.init();
        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> byteKeys = redisManager.keys(this.keyPrefix + "*");
        if (byteKeys != null && byteKeys.size() > 0) {
            for (byte[] bs : byteKeys) {
                Session s = (Session) SerializeUtil.deserialize(bs,Session.class);
                sessions.add(s);
            }
        }
        return sessions;
    }
 
    /**
     * ��ȡredis�е�session key
     * 
     * @param sessionId
     * @return
     */
    private String getRedisSessionKey(Serializable sessionId) {
        return this.keyPrefix + sessionId;
    }
 
    public RedisManager getRedisManager() {
        return redisManager;
    }
 
    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
 
}
	