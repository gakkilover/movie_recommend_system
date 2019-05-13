package com.zwk.movie_recommend;

import com.zwk.common.utils.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.JedisPool;

@SpringBootApplication
@MapperScan(basePackages = {"com.zwk.movie_recommend.dao.**"})
@ComponentScan(basePackages = {"com.zwk.movie_recommend.**.**"}) // 指定spring管理路径，就是那些bean 注解的路径
@Import(SpringContextUtils.class)
@EnableScheduling
@EnableCaching
//@EnableWebMvc
public class MovieRecommendApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        //启动项目
        SpringApplication.run(MovieRecommendApiApplication.class, args);

        //启动完成提示
        System.out.println("========================================================================================================================");
        System.out.println("                                                                                                                       ");
        System.out.println("                                                                                                                       ");
        System.out.println("                                                 毕业设计设计的不行可不能毕业                                               ");
        System.out.println("                                                                                                                       ");
        System.out.println("                                                                                                                       ");
        System.out.println("========================================================================================================================");

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MovieRecommendApiApplication.class);
    }

}

