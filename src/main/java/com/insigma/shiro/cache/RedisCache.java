package com.insigma.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;

import com.insigma.redis.RedisManager;
import com.insigma.redis.SerializeUtil;
  
public class RedisCache<K,V> implements Cache<K,V> {  
  
	
	private Log log = LogFactory.getLog(RedisCache.class);  
      
    /** 
     * The wrapped Jedis instance. 
     */  
    private RedisManager cache;  
      
    /** 
     * The Redis key prefix for the sessions  
     */  
    private String keyPrefix = "shiro_redis_cache:";  
      
    /** 
     * Returns the Redis session keys 
     * prefix. 
     * @return The prefix 
     */  
    public String getKeyPrefix() {  
        return keyPrefix;  
    }  
  
    /** 
     * Sets the Redis sessions key  
     * prefix. 
     * @param keyPrefix The prefix 
     */  
    public void setKeyPrefix(String keyPrefix) {  
        this.keyPrefix = keyPrefix;  
    }  
      
    /** 
     * ͨ��һ��JedisManagerʵ������RedisCache 
     */  
    public RedisCache(RedisManager cache){  
         if (cache == null) {  
             throw new IllegalArgumentException("Cache argument cannot be null.");  
         }  
         this.cache = cache;  
    }  
      
    /** 
     * Constructs a cache instance with the specified 
     * Redis manager and using a custom key prefix. 
     * @param cache The cache manager instance 
     * @param prefix The Redis key prefix 
     */  
    public RedisCache(RedisManager cache,String prefix){  
           
        this( cache );  
          
        // set the prefix  
        this.keyPrefix = prefix;  
    }  
      
    /** 
     * ���byte[]�͵�key 
     * @param key 
     * @return 
     */  
    private byte[] getByteKey(K key){  
        if(key instanceof String){  
            String preKey = this.keyPrefix + key;  
            return preKey.getBytes();  
        }else{  
            return SerializeUtil.serialize(key);  
        }  
    }  
      
    @Override  
    public V get(K key) throws CacheException {  
    	log.debug("����key��Redis�л�ȡ���� key [" + key + "]");  
        try {  
            if (key == null) {  
                return null;  
            }else{  
                byte[] rawValue = cache.get(getByteKey(key));  
                @SuppressWarnings("unchecked")  
                V value = (V)SerializeUtil.deserialize(rawValue);  
                return value;  
            }  
        } catch (Throwable t) {  
            throw new CacheException(t);  
        }  
  
    }  
  
    @Override  
    public V put(K key, V value) throws CacheException {  
        log.debug("����Ϣ��key [" + key + "]Ϊ�������浽redis��");  
         try {  
                cache.set(getByteKey(key), SerializeUtil.serialize(value));  
                return value;  
            } catch (Throwable t) {  
                throw new CacheException(t);  
            }  
    }  
  
    @Override  
    public V remove(K key) throws CacheException {  
    	log.debug("��redis��ɾ�� key [" + key + "]");  
        try {  
            V previous = get(key);  
            cache.del(getByteKey(key));  
            return previous;  
        } catch (Throwable t) {  
            throw new CacheException(t);  
        }  
    }  
  
    @Override  
    public void clear() throws CacheException {  
        try {  
            cache.flushDB();  
        } catch (Throwable t) {  
            throw new CacheException(t);  
        }  
    }  
  
    @Override  
    public int size() {  
        try {  
            Long longSize = new Long(cache.dbSize());  
            return longSize.intValue();  
        } catch (Throwable t) {  
            throw new CacheException(t);  
        }  
    }  
  
    @SuppressWarnings("unchecked")  
    @Override  
    public Set<K> keys() {  
        try {  
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");  
            if (CollectionUtils.isEmpty(keys)) {  
                return Collections.emptySet();  
            }else{  
                Set<K> newKeys = new HashSet<K>();  
                for(byte[] key:keys){  
                    newKeys.add((K)key);  
                }  
                return newKeys;  
            }  
        } catch (Throwable t) {  
            throw new CacheException(t);  
        }  
    }  
  
    @Override  
    public Collection<V> values() {  
        try {  
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");  
            if (!CollectionUtils.isEmpty(keys)) {  
                List<V> values = new ArrayList<V>(keys.size());  
                for (byte[] key : keys) {  
                    @SuppressWarnings("unchecked")  
                    V value = get((K)key);  
                    if (value != null) {  
                        values.add(value);  
                    }  
                }  
                return Collections.unmodifiableList(values);  
            } else {  
                return Collections.emptyList();  
            }  
        } catch (Throwable t) {  
            throw new CacheException(t);  
        }  
    }  
}  
