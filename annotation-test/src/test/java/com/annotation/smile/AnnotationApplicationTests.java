package com.annotation.smile;

import com.annotation.smile.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author smi1e
 * Date 2019/10/17 17:51
 * Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnnotationApplication.class)
public class AnnotationApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void Test1(){
        userService.getUserById(1);
    }
}
