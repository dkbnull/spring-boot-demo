package cn.wbnull.springbootdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfig
 *
 * @author dukunbiao(null)  2024-04-28
 * https://github.com/dkbnull/SpringBootDemo
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${demo.cors.allowed-origin-patterns:http://localhost:*}")
    private String[] allowedOriginPatterns;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //支持所有接口
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //支持域，仅允许配置的域，禁止与allowCredentials(true)配合使用"*"
                .allowedOriginPatterns(allowedOriginPatterns)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
