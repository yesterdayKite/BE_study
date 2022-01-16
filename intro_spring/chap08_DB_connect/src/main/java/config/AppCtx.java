package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.*;


@Configuration
@EnableTransactionManagement // @transactional이 붙은 메서드를 트랜잭션 범위에서 실행하는 기능 활성화함
public class AppCtx {
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){ // dataSource 객체 생성
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver"); // JDBC 드라이버 클래스 지정
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8"); // JDBC URL 지정
        // DB에 연결할때 사용자 계정과 암호
        ds.setUsername("spring5"); // DB에 연결할때 사용자 계정과 암호
        ds.setPassword("spring5");
        ds.setInitialSize(10);
        ds.setMaxActive(10); // 풀의 connection의 최대 수
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Bean
    public MemberDao memberDao(){
        return new MemberDao(dataSource());
    }

    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDao());
    }

    @Bean
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao());
        return pwdSvc;
    }

    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao(), memberPrinter());
    }

    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }
}
