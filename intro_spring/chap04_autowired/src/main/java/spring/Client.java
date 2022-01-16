package spring;

import org.springframework.beans.factory.DisposableBean; // bean 객체의 소멸과정이 필요할때
import org.springframework.beans.factory.InitializingBean; // bean 객체 생성후, 초기화 과정이 필요할때

public class Client implements InitializingBean, DisposableBean {
    private String host;

    public void setHost(String host){
        this.host = host;
    }

    @Override // bean 객체 생성후, 초기화 과정이 필요할때 알맞게 상속하여 구현
    public void afterPropertiesSet() throws Exception{
        System.out.println("Client.afterPropertiesSet() 실행");
    }

    public void send(){
        System.out.println("Client.send() to "+host);
    }

    @Override // bean 객체의 소멸과정이 필요할때 알맞게 상속하여 구현
    public void destroy() throws Exception{
        System.out.println("Client.destroy() 실행");
    }
}
