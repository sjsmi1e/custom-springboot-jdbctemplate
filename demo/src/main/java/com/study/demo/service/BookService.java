package com.study.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author smi1e
 * Date 2019/10/15 16:19
 * Description
 */
@Service
public class BookService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void find(int id) {
        List<Map<String, Object>> books = jdbcTemplate.queryForList("select * from `books` where BOOKID=" + id);
        books.forEach(x -> x.forEach((y, z) -> {
            System.out.println(y + " " + z.toString());
        }));
    }
}
