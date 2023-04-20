package com.web.dev;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/fonts/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/libs/gsap/**").addResourceLocations("classpath:/libs/gsap/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");

        registry.addResourceHandler("/blog/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/blog/fonts/**").addResourceLocations("classpath:/fonts/");
        registry.addResourceHandler("/blog/images/**").addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/blog/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/blog/libs/gsap/**").addResourceLocations("classpath:/libs/gsap/");
        registry.addResourceHandler("/blog/js/**").addResourceLocations("classpath:/js/");
    }
}
