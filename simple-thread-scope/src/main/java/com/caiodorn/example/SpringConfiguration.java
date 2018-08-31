package com.caiodorn.example;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    @ThreadScoped
    SampleBean getSampleBean() {
        return new SampleBean();
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new CustomBeanFactoryPostProcessor();
    }

}
