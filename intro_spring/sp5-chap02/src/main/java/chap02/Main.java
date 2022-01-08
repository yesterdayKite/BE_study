package chap02;

// AnnotationConfigApplicationContext 클래스는 자바 설정에서 정보를 읽어와, 빈 객체를 생성하고 관리한다.
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        // AppContext 클래스를 생성자 파라미터로 전달하여
        // AppContext에 정의한 @Bean 정보를 읽어와서 객체를 생성하고 초기화한다.
        // 이걸 돕는 것이 AnnotationConfigApplicationContext 클래스
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        // 자바 설정을 읽어와 bean객체를 검색한다. (첫번째 param:검색하려는 Bean의 이름, 두번째 param : 검색할 bean객체의 타입)
        Greeter g = ctx.getBean("greeter", Greeter.class); // 해당 bean을 검색하여 리턴한다
        String msg = g.greet("스프링"); // 읽어온 bean의 메소드를 실행한다.
        System.out.println(msg);
        ctx.close();
    }
}

