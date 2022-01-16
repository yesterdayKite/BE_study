package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.MemberDao;


@Configuration
public class AppCtx {
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){ // dataSource 객체 생성
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver"); // JDBC 드라이버 클래스 지정
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8"); // JDBC URL 지정
        // DB에 연결할때 사용자 계정과 암호
        ds.setUsername("spring5"); // DB에 연결할때 사용자 계정과 암호
        ds.setPassword("spring5");
        ds.setInitialSize(2);
        ds.setMaxActive(10); // 풀의 connection의 최대 수
        return ds;
    }

    @Bean
    public MemberDao memberDao(){
        return new MemberDao(dataSource());
    }
}
