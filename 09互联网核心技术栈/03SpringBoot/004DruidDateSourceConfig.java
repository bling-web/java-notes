package com.bfxy.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import java.sql.SQLException;

/**
 * 配置DruidDataSource,包括各种基础属性,过滤器,和事务管理
 */
@Configuration
//开启事务支持
@EnableTransactionManagement
public class DruidDateSourceConfig {

    private static Logger log= LoggerFactory.getLogger(DruidDateSourceConfig.class);

    @Resource
    private DruidDateSourceSet druidSettings;

    public static String DRIVER_CLASSNAME;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    //druid数据源监控台的一些配置
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean<Servlet> reg = new ServletRegistrationBean<>();
        reg.setServlet(new StatViewServlet());
        //以/druid开头的才可以访问,访问alibaba提供的druid可视化界面
        reg.addUrlMappings("/druid/*");
        //允许本地访问
        reg.addInitParameter("allow","127.0.0.1");
        //其他地址不允许访问
        reg.addInitParameter("deny","/deny");

        log.info("druid console manager init:{}",reg);
        return reg;
    }

    @Bean
    //druid连接池过滤器的配置,显示于监控台.
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        //排除以jsp,jpg,png等结尾的请求连接.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        log.info("druid filter register :{}",filterRegistrationBean);
        return filterRegistrationBean;
    }

    @Bean
    //配置Druid连接池
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(druidSettings.getDriverClassName());
        ds.setUrl(druidSettings.getUrl());
        ds.setUsername(druidSettings.getUsername());
        ds.setPassword(druidSettings.getPassword());
        ds.setInitialSize(druidSettings.getInitialSize());
        ds.setMinIdle(druidSettings.getMinIdle());
        ds.setMaxActive(druidSettings.getMaxActive());
        ds.setTimeBetweenEvictionRunsMillis(druidSettings.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(druidSettings.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(druidSettings.getValidationQuery());
        ds.setTestWhileIdle(druidSettings.isTestWhileIdle());
        ds.setTestOnBorrow(druidSettings.isTestOnBorrow());
        ds.setTestOnReturn(druidSettings.isTestOnReturn());
        ds.setPoolPreparedStatements(druidSettings.isPoolPreparedStatements());
        ds.setMaxPoolPreparedStatementPerConnectionSize(druidSettings.getMaxPoolPreparedStatementPerConnectionSize());
        ds.setFilters(druidSettings.getFilters());
        ds.setConnectionProperties(druidSettings.getConnectionProperties());
        log.info(" druid datasource config : {} ", ds);
        return ds;
    }
    @Bean
    //配置事务管理,在事务对象中传入数据库连接池
    public PlatformTransactionManager transactionManager() throws SQLException {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource());
        return manager;
    }


}
