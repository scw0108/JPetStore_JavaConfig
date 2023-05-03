package com.kazuki43zoo.jpetstore.config;

import com.kazuki43zoo.jpetstore.domain.Account;
import com.kazuki43zoo.jpetstore.mapper.*;
import com.kazuki43zoo.jpetstore.service.AccountService;
import com.kazuki43zoo.jpetstore.service.AccountUserDetails;
import com.kazuki43zoo.jpetstore.service.AccountUserDetailsService;
import com.kazuki43zoo.jpetstore.ui.controller.AccountController;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class AccountConfig {
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
    public MapperFactoryBean<AccountMapper> accountMapper() throws Exception {
        MapperFactoryBean<AccountMapper> factoryBean = new MapperFactoryBean<>(AccountMapper.class);
        DataSourceConfig datasourceConfig = new DataSourceConfig();
        factoryBean.setSqlSessionFactory(datasourceConfig.sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    public AccountUserDetailsService accountUserDetailsService() throws Exception {
        return new AccountUserDetailsService(accountMapper().getObject());
    }
}


