package com.annotation.smile.service;

import com.annotation.smile.annotation.MyCache;
import com.annotation.smile.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * @author smi1e
 * Date 2019/10/17 17:15
 * Description
 */
@Service
public class UserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @MyCache(key = "#id")
    public User getUserById(int id) {

        //1.从缓存中获取
//        Object cacheValue = redisTemplate.opsForValue().get(String.valueOf(id));
//        if (cacheValue != null) {
//            System.out.println("---------缓存中读取数据---------");
//            System.out.println(cacheValue);
//            return;
//        }
        //2.从数据库获取

        String sql = "select * from user where id=?";
        RowMapper<User> rowMapper=new BeanPropertyRowMapper<>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return user;



        //        maps.forEach(x -> x.forEach((y, z) -> {
//            System.out.println("---------数据库中读取数据---------");
//            redisTemplate.opsForValue().set(String.valueOf(id), x.get(y).toString());
//            System.out.println(y + ":" + x.get(y).toString());
//        }));
    }

}
