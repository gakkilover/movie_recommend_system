package com.zwk.movie_recommend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-02 09:13
 * @ Description：
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(InitApplicationRunner.class);
    public void run(ApplicationArguments args) throws Exception {
//        logger.info("猜猜我干了什么，嘻嘻嘻");
    }
}
