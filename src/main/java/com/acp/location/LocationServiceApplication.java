package com.acp.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@RestController
@EnableSwagger2
// @EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.acp.location.*" })
public class LocationServiceApplication {
	@Autowired
	BuildProperties buildProperties;
    public static void main(String[] args) {
	SpringApplication.run(
		LocationServiceApplication.class,
		args);
    }

    @GetMapping("/welcome")
    public String index() {
	return "Welcome to ICP Location Service Application";
    }
    
    @RequestMapping(value="/version")
    public String getVersion(){
        return buildProperties.getVersion()+":"+buildProperties.getName();
    }

    @Bean
    public Docket swaggerSubscriberApi10() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(
			RequestHandlerSelectors.basePackage(
				"com.acp.location.controller"))
		.paths(
			PathSelectors.any())
		.build().apiInfo(
			apiInfo());
    }

    private ApiInfo apiInfo() {
	return new ApiInfoBuilder().title(
		"ACP4.0 API s").description(
			"Documentation Location API v1.0")
		.termsOfServiceUrl(
			"termsofserviceurl")
		.license(
			"Apache License Version 2.0")
		.version(
			"2.0")
		.title(
			"Location API")
		.build();
    }

}
