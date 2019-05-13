package com.zwk.movie_recommend.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-15 11:00
 * @ Description：Spring Context 工具类
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {

        return applicationContext.getBean(clazz);

    }
    public static <T> T getBean(String name, Class<T> requireType){
        return applicationContext.getBean(name,requireType);
    }

    public boolean containsBean(String name){
        return applicationContext.containsBean(name);
    }

    public boolean isSingleton(String name){
        return applicationContext.isSingleton(name);
    }

    public Class<? extends Object> getType(String name){
        return applicationContext.getType(name);
    }

}
