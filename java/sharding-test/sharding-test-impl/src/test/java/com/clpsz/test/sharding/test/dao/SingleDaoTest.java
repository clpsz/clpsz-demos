package com.clpsz.test.sharding.test.dao;

import com.clpsz.test.sharding.test.util.TestUtil;
import com.clpsz.test.single.person.dao.PersonMapper;
import com.clpsz.test.single.person.domain.Person;
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
public class SingleDaoTest {
    @Autowired
    private PersonMapper personMapper;

    @Test
    public void testSelectSingle() {
        Person person = new Person();
        person.setName("himme44");
        person.setAge(22);

        int res = personMapper.insert(person);
        TestUtil.printResult(res);
    }
}
