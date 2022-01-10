package spring;

/*
직접 구현한 클래스가 아닌 외부에서 제공받은 클래스의 경우, 소스코드를 받지 않으면 InitializingBean, DisposaableBean 인터페이스를 구현/수정할 수 없다.
이럴 때, @Bean 태그에서 initMethod 속성, destroyMethod 속성을 이용해서
초기화 메소드 /소멸 메소드의 이름을 지정할 수 있다.

주의) 초기화 메서드가 두 번 불리지 않도록 할 ㅓ것
 */

import org.springframework.context.annotation.Bean;

@Bean(initMethod = "connect", destroyMethod = "close") // 직접 구현한 초기화/소멸 메서드를 지정해준다
public class Client2 {
    private String host;

    public void setHost(String host) {
        this.host = host;
    }

    public void connect(){
        System.out.println("Client2.send() 실행");
    }

    public void send(){
        System.out.println("Client2.send() to" + host);
    }

    public void close(){
        System.out.println("Client2.close() 실행");
    }
}
