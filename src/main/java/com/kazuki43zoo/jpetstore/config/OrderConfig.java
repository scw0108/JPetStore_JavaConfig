package com.kazuki43zoo.jpetstore.config;

import com.kazuki43zoo.jpetstore.mapper.ItemMapper;
import com.kazuki43zoo.jpetstore.mapper.OrderMapper;
import com.kazuki43zoo.jpetstore.mapper.SequenceMapper;
import com.kazuki43zoo.jpetstore.service.OrderService;
import com.kazuki43zoo.jpetstore.ui.controller.MyOrderController;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class OrderConfig {
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    @Bean
    OrderService orderService() throws Exception {
        return new OrderService(itemMapper2().getObject(),orderMapper().getObject(),sequenceMapper().getObject(),clock());
    }
    @Bean
    public MapperFactoryBean<ItemMapper> itemMapper2() throws Exception {
        MapperFactoryBean<ItemMapper> factoryBean = new MapperFactoryBean<>(ItemMapper.class);
        factoryBean.setSqlSessionFactory(dataSourceConfig.sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<OrderMapper> orderMapper() throws Exception {
        MapperFactoryBean<OrderMapper> factoryBean = new MapperFactoryBean<>(OrderMapper.class);
        factoryBean.setSqlSessionFactory(dataSourceConfig.sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<SequenceMapper> sequenceMapper() throws Exception {
        MapperFactoryBean<SequenceMapper> factoryBean = new MapperFactoryBean<>(SequenceMapper.class);
        factoryBean.setSqlSessionFactory(dataSourceConfig.sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    MyOrderController myOrderController() throws Exception {
        CatalogConfig catalogConfig = new CatalogConfig();
        return new MyOrderController(orderService(),catalogConfig.cart());
    }
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

}
