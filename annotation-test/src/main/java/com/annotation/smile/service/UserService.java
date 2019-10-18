package com.annotation.smile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * @author smi1e
 * Date 2019/10/17 17:15
 * Description
 */
@Service
public class UserService {

    @Autowired


    @Autowired
    JdbcTemplate jdbcTemplate;

    public void getUserById(int id) {

        //1.从缓存中获取
        String cacheUser = jedis.get(String.valueOf(id));
        if (cacheUser != null) {
            System.out.println("---------缓存中读取数据---------");
            System.out.println(cacheUser);
            return;
        }
        //2.从数据库获取
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user where id=" + id);
        maps.forEach(x -> x.forEach((y, z) -> {
            System.out.println("---------数据库中读取数据---------");
            jedis.set(y, String.valueOf(z));
            System.out.println(z.toString());
        }));
    }

}
