package com.clpsz.test.sharding.test.dao;

import com.clpsz.test.sharding.pay_order.dao.PayOrderMapper;
import com.clpsz.test.sharding.pay_order.domain.PayOrder;
import com.clpsz.test.sharding.test.util.TestUtil;
import com.clpsz.test.single.person.dao.PersonMapper;
import com.clpsz.test.single.person.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by clpsz on 2017/3/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-application.xml")
public class ShardingDaoTest {
    @Autowired
    private PayOrderMapper payOrderMapper;

    @Test
    public void testSelectSharding() {
        PayOrder result = payOrderMapper.selectByPrimaryKey("CPO2017031422200222");
        TestUtil.printResult(result);
    }

    @Test
    public void testInsertSharding() {
        PayOrder payOrder = new PayOrder();

        payOrder.setPayOrderId("CPO2017031422200222");
        payOrder.setParentPayOrderId("PPO2017031422200222");
        payOrder.setUid(30222);
        payOrder.setAmount(0L);
        payOrder.setPayWay(0);
        payOrder.setPayType(10);
        payOrder.setPayState(10);
        payOrder.setSucTradeId("");
        payOrder.setVersion(0);
        payOrder.setCreateTime(new Date());
        payOrder.setModifyTime(new Date());

        int result = payOrderMapper.insert(payOrder);
        TestUtil.printResult(result);
    }
}
