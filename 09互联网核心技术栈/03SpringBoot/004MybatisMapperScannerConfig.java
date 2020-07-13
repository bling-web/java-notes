package com.bfxy.springboot.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 配置扫描包配置,也就是指定对应的sql语句在哪
 */
@Configuration
//这个注解是标注了解析顺序,该类在加载完MybatisDataSourceConfig之后才可以加载
@AutoConfigureAfter(MyabtisDataSourceConfig.class)
public class MybatisMapperScannerConfig {

    @Bean
    //配置扫描类,配置了该扫描类后会自动将每一个mapper类自动注入spring工厂中,并注入到sqlSessionFactory对象中.

    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        scannerConfigurer.setBasePackage("com.bfxy.springboot.mapper");
        return scannerConfigurer;
    }



}
