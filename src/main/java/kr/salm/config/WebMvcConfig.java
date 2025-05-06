package kr.salm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /img/** 요청을 실제 파일 시스템의 /data/salm-img-uploads/ 로 매핑
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/data/salm-img-uploads/");
    }
}
