package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.MemberDao;
import spring.MemberPrinter;

@Configuration
@Import({AppConfig1.class, AppConfig2.class}) // @Import 어노테이션은 두개 이상의 설정클래스 지정
public class AppConfImport {
    @Bean // bean 객체 생성
    public MemberDao memberDao(){
        return new MemberDao();
    }
    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }
}
