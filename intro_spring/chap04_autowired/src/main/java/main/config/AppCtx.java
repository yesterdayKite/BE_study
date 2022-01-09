package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.*;

@Configuration // spring 설정 클래스를 의미하는 어노테이션
@ComponentScan(basePackages = {"spring"}) // @Component 어노테이션을 붙인 클래스를 스캔할 수 있또록, 설정 클래스에 적용해야하는 어노테이션
public class AppCtx {

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        // 실수를 직접 등록한다
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1(){
        return new MemberPrinter();
    }

    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2(){
        return new MemberSummaryPrinter();
    }

}

