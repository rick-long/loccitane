package org.spa;

import org.spa.utils.ServletUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("init upload file resources.");
        registry.addResourceHandler("/download/**")
                .addResourceLocations("file:///" + ServletUtil.UPLOAD_FILE_PATH);
    }
}
