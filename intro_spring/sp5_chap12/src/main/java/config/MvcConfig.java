package config;

import controller.RegisterRequestValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    // global 범위 validator 설정 (@Valid 애노테이션 사용하여, Validator 적용할 ㅅ ㅜ있게 된다.)
    @Override
    public Validator getValidator(){
        return new RegisterRequestValidator();
    }

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

    // message source 설정 추가
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource ms =
                new ResourceBundleMessageSource();
        ms.setBasenames("message.label"); // message 패키지에 속한 label 프로퍼티 파일로부터 메세지를 읽어옴을 설정
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
