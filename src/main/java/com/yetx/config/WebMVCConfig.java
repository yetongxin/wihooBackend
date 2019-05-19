package com.yetx.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yetx.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {

    @Value("${avatar.space}")
    private String avatarSpace;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("头像地址："+avatarSpace);
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+avatarSpace+"/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    //注册拦截器
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //设置（模糊）匹配的url
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/user/**");
        urlPatterns.add("/question/**");

//        urlPatterns.add("/api/v1/userinfo/*");

        registry.addInterceptor(tokenInterceptor()).addPathPatterns(urlPatterns)
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/article/*")
                .excludePathPatterns("/question/all/time","/question/user","/question/search");

        super.addInterceptors(registry);
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        converter.setObjectMapper(mapper);
        return converter;
    }//添加转换器

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        //将我们定义的时间格式转换器添加到转换器列表中,
        // 这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
        converters.add(jackson2HttpMessageConverter());
    }
}
