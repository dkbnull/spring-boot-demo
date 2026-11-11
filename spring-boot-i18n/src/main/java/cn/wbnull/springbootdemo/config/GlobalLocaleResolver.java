package cn.wbnull.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * GlobalLocaleResolver
 *
 * @author dukunbiao(null)  2024-03-28
 * https://github.com/dkbnull/SpringBootDemo
 */
@Configuration
public class GlobalLocaleResolver implements LocaleResolver {

    /**
     * 解析请求
     *
     * @param request
     * @return
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //默认地区
        Locale locale = request.getLocale();

        //获取请求中的语言参数
        String language = request.getParameter("l");
        if (!StringUtils.isEmpty(language)) {
            //zh_CN 国家_地区
            String[] values = language.split("_");
            if (values.length == 2) {
                locale = new Locale(values[0], values[1]);
            } else if (values.length == 1) {
                locale = new Locale(values[0]);
            }
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }

    @Bean
    public LocaleResolver localeResolver() {
        return new GlobalLocaleResolver();
    }
}
