package com.mnuo.forpink.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class ForpinkSwaggerConfig implements WebMvcConfigurer{
	/**
	 * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
//		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	/**
	 * 可以注入多个doket，也就是多个版本的api，可以在看到有三个版本groupName不能是重复的，
	 * v1和v2是ant风格匹配，配置文件
	 * @return
	 */
    @Bean
    public Docket api() {
    	return new Docket(DocumentationType.SWAGGER_2)
        .pathMapping("/")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.mnuo.forpink"))
        .paths(PathSelectors.any())
        .build().apiInfo(new ApiInfoBuilder()
                .title("for pink tomorrow")
                .description("for pink")
                .version("9.0")
                .contact(new Contact("油炸肉块","blog.csdn.net","aaa@gmail.com"))
                .license("The Apache License")
                .licenseUrl("http://www.baidu.com")
                .build());
    }


    private ApiInfo apiInfo1() {
        return new ApiInfoBuilder()
                .title("exampleApi 0.01")
                .termsOfServiceUrl("www.example.com")
                .contact(new Contact("liumei","http://blog.csdn.net/pc_gad","hilin2333@gmail.com"))
                .version("v0.01")
                .build();
    }
}
