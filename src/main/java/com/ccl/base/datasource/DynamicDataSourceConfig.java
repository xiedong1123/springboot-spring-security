package com.ccl.base.datasource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DynamicDataSourceConfig {
	
	@Autowired
	private Environment env;
	
	public HikariDataSource dataSourceOne() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		hikariDataSource.setUsername(env.getProperty("spring.datasource.username"));
		hikariDataSource.setPassword(env.getProperty("spring.datasource.password"));
		return hikariDataSource;
	}
	
	public HikariDataSource dataSourceTwo() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		hikariDataSource.setUsername(env.getProperty("spring.datasource.username"));
		hikariDataSource.setPassword(env.getProperty("spring.datasource.password"));
		return hikariDataSource;
	}
	
	@Bean
	public DynamicDataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		
		//放置两个数据源
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.one.getValue(), dataSourceOne());
		targetDataSources.put(DBTypeEnum.two.getValue(), dataSourceTwo());
		dynamicDataSource.setTargetDataSources(targetDataSources);
		
		//默认选择第一个数据源(作为启动所需数据源)
		DbContextHolder.setDbType(DBTypeEnum.one);
		
		return dynamicDataSource;
	}
}
