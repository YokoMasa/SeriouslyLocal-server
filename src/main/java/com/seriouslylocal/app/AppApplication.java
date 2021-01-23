package com.seriouslylocal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AppApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		WebMvcConfigurer.super.addViewControllers(registry);
		registry.addViewController("/admin").setViewName("forward:/admin/index.html");
		registry.addViewController("/").setViewName("redirect:/place_article");
	}

}
