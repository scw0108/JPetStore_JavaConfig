package com.kazuki43zoo.jpetstore.config;

import com.kazuki43zoo.jpetstore.mapper.CategoryMapper;
import com.kazuki43zoo.jpetstore.mapper.ItemMapper;
import com.kazuki43zoo.jpetstore.mapper.ProductMapper;
import com.kazuki43zoo.jpetstore.service.CatalogService;
import com.kazuki43zoo.jpetstore.ui.Cart;
import com.kazuki43zoo.jpetstore.ui.ProductSearchCriteria;
import com.kazuki43zoo.jpetstore.ui.controller.CartController;
import com.kazuki43zoo.jpetstore.ui.controller.CatalogController;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfig {
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    @Bean
    CatalogService catalogService() throws Exception {
        return new CatalogService(categoryMapper().getObject(), itemMapper().getObject(), productMapper().getObject());
    }
    @Bean
    CartController cartController() throws Exception {
        return new CartController(catalogService(), cart());
    }
    @Bean
    Cart cart(){
        return new Cart();
    }
    @Bean
    public MapperFactoryBean<CategoryMapper> categoryMapper() throws Exception {
        MapperFactoryBean<CategoryMapper> factoryBean = new MapperFactoryBean<>(CategoryMapper.class);
        factoryBean.setSqlSessionFactory(dataSourceConfig.sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<ItemMapper> itemMapper() throws Exception {
        MapperFactoryBean<ItemMapper> factoryBean = new MapperFactoryBean<>(ItemMapper.class);
        factoryBean.setSqlSessionFactory(dataSourceConfig.sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    public MapperFactoryBean<ProductMapper> productMapper() throws Exception {
        MapperFactoryBean<ProductMapper> factoryBean = new MapperFactoryBean<>(ProductMapper.class);
        factoryBean.setSqlSessionFactory(dataSourceConfig.sqlSessionFactory());
        return factoryBean;
    }
    @Bean
    CatalogController catalogController() throws Exception {
        return new CatalogController(catalogService(), productSearchCriteria());
    }

    @Bean
    ProductSearchCriteria productSearchCriteria(){
        return new ProductSearchCriteria();
    }
}
