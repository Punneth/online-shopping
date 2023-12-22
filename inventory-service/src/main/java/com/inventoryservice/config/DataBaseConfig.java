package com.inventoryservice.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataBaseConfig {

    @Autowired
    private Environment environment;


//    @Qualifier("mySqlJdbcTemplate")
    @Bean
    public DataSource mysqlDataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dataSource.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        dataSource.setMaximumPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.maxActive")));
        dataSource.setConnectionInitSql(environment.getProperty("spring.datasource.validationQuery"));
        dataSource.setMinimumIdle(Integer.parseInt(environment.getProperty("spring.datasource.minIdle")));

        return dataSource;
    }


//    @Qualifier("namedJdbcTemplate")
    @Bean
    public NamedParameterJdbcTemplate namedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(mysqlDataSource());
    }

//    @Bean
//    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

}
