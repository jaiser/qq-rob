package com.jaiser.qqrob.utils;

import com.jaiser.qqrob.config.JedisConfig;
import io.lettuce.core.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;


/**
 * @author jaiser
 */
@Component
public class JedisUtil {

    private static JedisPool jedisPool = null;

    @Autowired
    private JedisConfig parameters;

    /**
     * 初始化redisWrapper (PostConstruct注解相当于静态代码库,方法会在类初始化的时候 进行执行)
     *
     * @throws RedisException
     */
    @PostConstruct
    public void init() throws RedisException {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(parameters.getMaxIdle());
            config.setMaxTotal(parameters.getMaxActive());
            config.setMaxWaitMillis(parameters.getMaxWaitMillis());
            jedisPool = new JedisPool(config, parameters.getHost(), parameters.getPort(), 2000);
        } catch (Exception e) {
            throw new RedisException("初始化redisPool失败");
        }
    }

    public static JedisPool getJedisPool() {
        if (jedisPool == null) {
            throw new RedisException("获取redisPool失败，请联系管理员");
        }
        return jedisPool;
    }
}
