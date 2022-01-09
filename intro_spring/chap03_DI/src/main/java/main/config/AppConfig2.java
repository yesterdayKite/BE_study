package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.*;

@Configuration
public class AppConfig2 {
    /*
    Autowired : 자동 의존주입
    해당 어노테이션을 붙인 타입의 빈을 찾아서, 필드에 할당한다.
    (여기에선 MemberDao 타입의 빈을 memberDao 필드에 할당한다.)
     */
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberPrinter memberPrinter;

    @Bean // bean 객체 생성
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService(memberDao); // 생성자를 사용하여 의존객체 주입
    }
    @Bean // bean 객체 생성
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc =  new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao); // setter를 사용하여 의존객체 주입
        return pwdSvc;
    }
    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao, memberPrinter); // 두개 이상의 인자를 받는 생성자를 사용하는 경우에도 각 파라미터에 해당하는 메서드를 호출하여 의존객체 주입한다.
    }
    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        //setter를 사용하여 memberDao빈과 memberPrinter빈을 주입한다.
        infoPrinter.setMemberDao(memberDao);
        infoPrinter.setPrinter(memberPrinter);
        return infoPrinter;
    }
    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
