package com.bfxy.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;

/**
 * 配置SqlSessionFactory对象,主要是将配置好的DruidDataSource传进去.
 */
@Configuration
public class MyabtisDataSourceConfig {
    @Resource
    private DruidDataSource dataSource;
    private SqlSessionTemplate sqlSessionTemplate;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        //这个类可产生SqlSessionFactory对象
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //把设置好的数据库连接池传进去
        sqlSessionFactoryBean.setDataSource(dataSource);
        //添加XML目录,将指定的资源位置路径解析成一个或多个匹配的资源,如果不加就找不到对应的xml文件.
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        //就是转换一下,将指定的路径转换成一个对象,如果需要使用这个路径,传入该对象即可
        //设置mybatis找mapping配置时,也就是指定mapper配置文件的路径,每一个dao一个配置文件.
        //这个配置其实也可以在application.properties中配置.
        sqlSessionFactoryBean.setMapperLocations(patternResolver.getResources("classpath:com/bfxy/springboot/mapping/*.xml"));

        SqlSessionFactory factory = null;
        try {
            factory = sqlSessionFactoryBean.getObject();
            //设置sqlSessionFactory开启缓存.
            factory.getConfiguration().setCacheEnabled(true);
            sqlSessionTemplate = new SqlSessionTemplate(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }


    @Bean
    //返回SqlSession的一个模板对象.
    public SqlSessionTemplate sqlSessionTemplate() {
        return sqlSessionTemplate;
    }
}
