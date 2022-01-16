package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // spring MVC설정을 활성화 (이 annotation은 내부적으로 MVC에 필수적인 다양한 빈 설정을 대신 구성해준다!)
public class MvcConfig implements WebMvcConfigurer { //  WebMvcConfigurer는 스프링MVC의 개별 설정을 조정할때 사용한다.


    // DispatcherServlet의 매핑경로를 '/'로 주었을 때, JSP/HTML/CSS등을 올바르게 처리하기 위한 설정을 추가한다.
    @Override
    public void configureDefaultServletHandling(
        DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // JSP를 이용해서 컨트롤러의 실행 결과를 보여주기 위한 설정을 추가한다.
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        registry.jsp("/WEB-INF/view/",".jsp"); //JSP를 이용해서 컨트롤러의 실행 결과를 보여주기위한 설정을 추가한다.
    }
}
