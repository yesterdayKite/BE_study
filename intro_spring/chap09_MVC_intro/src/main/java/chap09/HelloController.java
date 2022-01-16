package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 이 어노테이션을 적용한 클래스는 스프링 MVC에서 컨트롤러로 사용된다.
public class HelloController {

    @GetMapping("/hello") // 메서드가 처리할 요청 경로를 지정한다. (여기에선 /hello 경로로 들어온 요청을 hello() 메서드를 이용하여 처리한다.)
    public String hello(Model model, // Model 파라미터는 컨트롤러 처리 결과를 뷰에 전달할때 사용한다.
                        @RequestParam(value = "name", required = false) String name) { // @RequestParam 어노테이션은 HTTP 요청 파라미터의 값을 메서드의 파라미터 값으로 전달될때 사용된다. (여기에선 name요청 파라미터의 값을 name파라미터에 전달함)
        model.addAttribute("greeting", "안녕하세요, " + name); // greeting이라는 모델 속성에 값을 설정한다. (값 = "안녕하세요 + name 파라미터의 값")
        return "hello"; // 컨트롤러의 처리 결과를 보여줄 뷰 이름으로 "hello" 사용
    }
}


