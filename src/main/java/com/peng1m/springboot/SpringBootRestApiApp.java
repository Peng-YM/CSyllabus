package com.peng1m.springboot;

import com.peng1m.springboot.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;


@SpringBootApplication(scanBasePackages={"com.peng1m.springboot"})
public class SpringBootRestApiApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);
	}

	@Bean
    public FilterRegistrationBean jwtFilter(){
	    final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(new JwtFilter());
        return registrationBean;
    }

	@Bean
	public FilterRegistrationBean encodingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		registrationBean.setFilter(characterEncodingFilter);
		return registrationBean;
	}

}
