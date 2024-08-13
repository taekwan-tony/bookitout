package kr.co.bookItOut;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.bookItOut.error.controller.AdminIntercepter;
import kr.co.bookItOut.error.controller.LoginIntercepter;

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
				.addPathPatterns("/member/mypage",
						"/member/myInfo",
						"/member/myOrder",
						"/member/myBoard",
						"/member/myReview",
						"/member/logout",
						"/board/writeFrm",
						"/board/editorFrm",
						"/board/write",
						"/board/delete",
						"/board/updateFrm",
						"/board/update",
						"/board/insertComment",
						"/board/updateComment",
						"/board/delteComment",
						"/book/insertComment",
						"/book/updateComment",
						"/book/deleteComment",
						"/cart/**",
						"/myPage/myPage",
						"/Question/**"
						);
				
	
		registry.addInterceptor(new AdminIntercepter())
				.addPathPatterns("/admin/**",
						"/notice/writeNoticeFrm",
						"/notice/writeNotice",
						"/notice/updateFrm","/notice/deleteNotice")
				.excludePathPatterns("/member/login","/admin/login");
	}
	
}