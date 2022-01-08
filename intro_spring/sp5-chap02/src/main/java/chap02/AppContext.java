package chap02;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {
    /*
    스프링은 객체를 생성하고 초기화하는 기능제공
    스프링이 생성하는 객체 = Bean객체
     */
    @Bean // bean annotation을 붙이면, 해당 메서드가 생성한 객체를 스프링이 관리하는 빈 객체로 등록한다.
    public Greeter greeter(){ // 빈 Bean객체에 정보를 담는 메소드
        Greeter g = new Greeter();
        g.setFormat("%s, 안녕하세요!");
        return g;
    }
}