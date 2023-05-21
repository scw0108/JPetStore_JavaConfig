package com.kazuki43zoo.jpetstore.config;

import com.kazuki43zoo.jpetstore.mapper.*;
import com.kazuki43zoo.jpetstore.service.AccountService;
import com.kazuki43zoo.jpetstore.service.AccountUserDetailsService;
import com.kazuki43zoo.jpetstore.service.CatalogService;
import com.kazuki43zoo.jpetstore.service.OrderService;
import com.kazuki43zoo.jpetstore.ui.Cart;
import com.kazuki43zoo.jpetstore.ui.EventListeners;
import com.kazuki43zoo.jpetstore.ui.Favourite;
import com.kazuki43zoo.jpetstore.ui.ProductSearchCriteria;
import com.kazuki43zoo.jpetstore.ui.controller.AccountController;
import com.kazuki43zoo.jpetstore.ui.controller.CartController;
import com.kazuki43zoo.jpetstore.ui.controller.CatalogController;
import com.kazuki43zoo.jpetstore.ui.controller.MyOrderController;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;
import java.time.Clock;

@Configuration
public class Config {
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
        MapperFactoryBean<CategoryMapper> factoryBean = new MapperFactoryBean<>(CategoryMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<ItemMapper> itemMapper() throws Exception {
        MapperFactoryBean<ItemMapper> factoryBean = new MapperFactoryBean<>(ItemMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<ProductMapper> productMapper() throws Exception {
        MapperFactoryBean<ProductMapper> factoryBean = new MapperFactoryBean<>(ProductMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<OrderMapper> orderMapper() throws Exception {
        MapperFactoryBean<OrderMapper> factoryBean = new MapperFactoryBean<>(OrderMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<SequenceMapper> sequenceMapper() throws Exception {
        MapperFactoryBean<SequenceMapper> factoryBean = new MapperFactoryBean<>(SequenceMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    AccountController accountController() throws Exception {
        return new AccountController(accountService());
    }
    @Bean
    public AccountService accountService() throws Exception {
        return new AccountService(accountMapper().getObject(), passwordEncoder());
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }
    @Bean
    public AccountUserDetailsService accountUserDetailsService() throws Exception {
        return new AccountUserDetailsService(accountMapper().getObject());
    }


    @Bean
    CatalogService catalogService() throws Exception {
        return new CatalogService(categoryMapper().getObject(), itemMapper().getObject(), productMapper().getObject());
    }
    @Bean
    Cart cart(){
        return new Cart();
    }
    @Bean
    CartController cartController() throws Exception {
        return new CartController(catalogService(), cart());
    }
    @Bean
    ProductSearchCriteria productSearchCriteria(){
        return new ProductSearchCriteria();
    }
    @Bean
    CatalogController catalogController() throws Exception {
        return new CatalogController(catalogService(), productSearchCriteria());
    }
    @Bean
    EventListeners eventListeners() throws Exception {
        return new EventListeners(catalogService(),favourite());
    }
    @Bean
    Favourite favourite(){
        return new Favourite();
    }

    @Bean
    OrderService orderService() throws Exception {
        return new OrderService(itemMapper().getObject(),orderMapper().getObject(),sequenceMapper().getObject(),clock());
    }

    @Bean
    MyOrderController myOrderController() throws Exception {
        return new MyOrderController(orderService(),cart());
    }
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
