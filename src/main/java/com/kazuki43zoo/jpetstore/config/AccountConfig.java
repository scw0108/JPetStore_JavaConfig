package com.kazuki43zoo.jpetstore.config;

import com.kazuki43zoo.jpetstore.mapper.*;
import com.kazuki43zoo.jpetstore.service.AccountService;
import com.kazuki43zoo.jpetstore.ui.controller.AccountController;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class AccountConfig {
    @Bean
    AccountController accountController() throws Exception {
        System.out.println("Test");
        AccountService a = accountService();
        return new AccountController(a);
    }
    @Bean
    public AccountService accountService() throws Exception {
        return new AccountService(accountMapper().getObject() ,passwordEncoder());
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public MapperFactoryBean<AccountMapper> accountMapper() throws Exception {
        MapperFactoryBean<AccountMapper> factoryBean = new MapperFactoryBean<>(AccountMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:file:~/db/jpetstore");
        return dataSource;
    }
    @Bean
    public MapperFactoryBean<CategoryMapper> categoryMapper() throws Exception {
        System.out.println("Hi8");
        MapperFactoryBean<CategoryMapper> factoryBean = new MapperFactoryBean<>(CategoryMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<ItemMapper> itemMapper() throws Exception {
        System.out.println("Hi7");
        MapperFactoryBean<ItemMapper> factoryBean = new MapperFactoryBean<>(ItemMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<ProductMapper> productMapper() throws Exception {
        System.out.println("Hi6");
        MapperFactoryBean<ProductMapper> factoryBean = new MapperFactoryBean<>(ProductMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<OrderMapper> orderMapper() throws Exception {
        System.out.println("Hi5");
        MapperFactoryBean<OrderMapper> factoryBean = new MapperFactoryBean<>(OrderMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<SequenceMapper> sequenceMapper() throws Exception {
        System.out.println("Hi4");
        MapperFactoryBean<SequenceMapper> factoryBean = new MapperFactoryBean<>(SequenceMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
}
