package kr.co.bookItOut;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.bookItOut.error.controller.LoginIntercepter;
import kr.co.iei.util.AdminInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	@Value("${file.root}")
	private String root;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/**").addResourceLocations("classpath:/templates/","classpath:/static/");
		
		registry
			.addResourceHandler("/photo/**")
			.addResourceLocations("file:///"+root+"/photo/");
		

		registry
			.addResourceHandler("/book/**")
			.addResourceLocations("file:///"+root+"/book/");
		
		registry
		.addResourceHandler("/question/**")
		.addResourceLocations("file:///"+root+"/question/");
		
		registry
		.addResourceHandler("/board/**")
		.addResourceLocations("file:///"+root+"/board/");
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginIntercepter())
				.addPathPatterns("/member/)
				.excludePathPatterns("/notice/list","/notice/view","/notice/search","/notice/filedown","/notice/editor/**");
	
		registry.addInterceptor(new AdminInterceptor())
				.addPathPatterns("/admin/**");
	}
	
}