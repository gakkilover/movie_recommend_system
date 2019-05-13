//package com.zwk.movie_recommend.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//
///**
// * @ Author     ：zwk
// * @ Email      ：zwk0@qq.com
// * @ Date       ：Created in 2019-04-08 14:48
// * @ Description：
// */
//@EnableWebMvc
//@Configuration
//public class WebJSPConfig  extends WebMvcConfigurerAdapter {
//    private static final Logger logger = LoggerFactory.getLogger(WebJSPConfig.class);
//
//    /**
//     * 多模块的jsp访问，默认是src/main/webapp，但是多模块的目录只设置yml文件或propeerties文件不行
//     *
//     * @return
//     */
//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//        logger.info("****************/WEB-INF/jsp/*****************************************");
//        return viewResolver;
//    }
//
//
//    /**
//     * 指定默认文件的地址，jsp页面引入js和css的时候就不用管项目路径了
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/static/**")
////                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
////        super.addResourceHandlers(registry);
//    }
//}
