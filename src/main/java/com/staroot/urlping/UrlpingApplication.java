package com.staroot.urlping;

import com.staroot.urlping.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UrlpingApplication {

	public static void main(String[] args) {

		SpringApplication.run(UrlpingApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new LoginFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
