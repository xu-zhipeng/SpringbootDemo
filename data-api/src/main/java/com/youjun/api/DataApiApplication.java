package com.youjun.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScans;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2020/10/19
 */
@SpringBootApplication
@ComponentScans(value = {
//        @ComponentScan("com.youjun.common"),
//        @ComponentScan("com.youjun.security"),
})
public class DataApiApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DataApiApplication.class, args);
        /*String[] beanNames = run.getBeanDefinitionNames();
        //String[] beanNames = ctx.getBeanNamesForAnnotation(RestController.class);//所有添加该注解的bean
        System.out.println("bean总数:{}" + run.getBeanDefinitionCount());
        for (String str : beanNames) {
            System.out.println(str);
        }*/
    }
}
