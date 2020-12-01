//package com.youjun.api.config;
//
//import com.youjun.api.util.InfluxDbUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * <p>
// *
// * </p>
// *
// * @author kirk
// * @since 2020/10/22
// */
//@Configuration
//public class InfluxDbConfig {
//    @Value("${spring.influx.url:''}")
//    private String influxDBUrl;
//    @Value("${spring.influx.user:''}")
//    private String userName;
//
//    @Value("${spring.influx.password:''}")
//    private String password;
//
//    @Value("${spring.influx.database:''}")
//    private String database;
//
//    @Bean
//    public InfluxDbUtils influxDbUtils() {
//        return new InfluxDbUtils(userName, password, influxDBUrl, database, "autogen");
//    }
//}
