package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        registry.jsp("/WEB-INF/view/", ".jsp");
    }
    /*
    요청경로-뷰이름의 단순연결을 위해 특별한 로직이 없는 컨트롤러를 만드는 것은 성가신 일이다.
    WebMvcConfigurere 인터페이스의 addViewControllers()를 통해
    컨트롤러구현없이 간단히 요청경로-뷰이름을 연결할 수 있다!
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }

}
