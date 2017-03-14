package com.clpsz.test.sharding.config;

import com.clpsz.test.sharding.MySqlSessionTemplate;
import com.clpsz.test.sharding.ShardPlugin;
import com.google.common.collect.Maps;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by clpsz on 2017/3/13.
 */
@Configuration
public class MyBatisConfig implements ApplicationContextAware {
    private ApplicationContext context;


    @Bean
    public DataSource dataSource() {
        return new PooledDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.220.130:3306/test", "root", "root");
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(context.getResources("classpath*:mappers/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.clpsz.test.sharding.pay_order.domain,com.clpsz.test.single.person.domain");


        ShardPlugin shardPlugin = new ShardPlugin();
        Map<String, String> table2DBMap = Maps.newHashMap();
        table2DBMap.put("t_pay_order", "pay_order");
        shardPlugin.setTable2DB(table2DBMap);

        List<Interceptor> interceptors = new ArrayList<Interceptor>();
        interceptors.add(shardPlugin);
        Interceptor[] interceptorsArray = interceptors.toArray(new Interceptor[interceptors.size()]);
        sessionFactory.setPlugins(interceptorsArray);

        return new SqlSessionTemplate(sessionFactory.getObject());
    }

    @Bean
    public MapperScannerConfigurer mpperScannnerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        msc.setBasePackage("com.clpsz.test.sharding.pay_order.dao,com.clpsz.test.single.person.dao");
        return msc;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
