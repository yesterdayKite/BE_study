package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.*;

@Configuration // spring 설정 클래스를 의미하는 어노테이션
public class AppCtx {
    @Bean // bean 객체 생성
    public MemberDao memberDao(){
        return new MemberDao();
    }
    @Bean // bean 객체 생성
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService(memberDao()); // 생성자를 사용하여 의존객체 주입
    }
    @Bean // bean 객체 생성
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc =  new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao()); // setter를 사용하여 의존객체 주입
        return pwdSvc;
    }
    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }
    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao(), memberPrinter()); // 두개 이상의 인자를 받는 생성자를 사용하는 경우에도 각 파라미터에 해당하는 메서드를 호출하여 의존객체 주입한다.
    }
    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        //setter를 사용하여 memberDao빈과 memberPrinter빈을 주입한다.
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }
}
