package com.idrsv.spring.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;


/**
 * Настройка конфигурации проекта через java код вместо xml файла
 * Всё тоже самое что в spring_course_mvc_hibernate_aop applicationContext.xml
 * */

@Configuration
@ComponentScan(basePackages = "com.idrsv.spring.rest")    //пакет для скана на бины
@EnableWebMvc                                                    //замена mvc:annotation-driven
@EnableTransactionManagement                                     //замена tx:annotation-driven transaction-manager
public class MyConfig {

    @Bean    //настройка бина dataSource
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try
        {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC");
            dataSource.setUser("root");
            dataSource.setPassword("dGCvkabb97");
        }
        catch (PropertyVetoException e)
        {
            e.printStackTrace();
        }

        return dataSource;
    }

    @Bean    //настройка бина sessionFactory
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.idrsv.spring.rest.entity");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean    //настройка бина transactionManager
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }
}
