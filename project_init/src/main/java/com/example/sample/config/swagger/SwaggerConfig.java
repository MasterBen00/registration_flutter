package com.example.sample.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Value("${swagger.controller.package}")
	private String basePackage;

	@Value("${swagger.host.url}")
	private String hostUrl;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host(hostUrl)
				.groupName("Spring Actuator")
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {

		return new ApiInfoBuilder()
				.title("Swagger API Doc")
				.description("More description about the API")
				.version("1.0.0")
				.build();
	}
}
