package com.clpsz.test.sharding.test.example;

import com.clpsz.test.sharding.example.ExampleBean;
import com.clpsz.test.sharding.test.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by clpsz on 2017/3/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-application.xml")
public class SpringApplicationTest {
    @Autowired
    private ExampleBean exampleBean;


    @Test
    public void testXxx() {
        TestUtil.printResult(exampleBean.getExampleMst());
    }
}
