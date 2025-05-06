package kr.salm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 시스템 이미지 (로고 등) 경로 /img/** -> classpath:/static/img/ 디렉토리로 매핑
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");

        // 게시글 이미지 경로 /data/** -> /data/salm-img-uploads/ 외부 경로로 매핑
        registry.addResourceHandler("/data/**")
                .addResourceLocations("file:/data/salm-img-uploads/");
    }
}

