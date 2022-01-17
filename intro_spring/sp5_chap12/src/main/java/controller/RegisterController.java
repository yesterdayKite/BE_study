package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.DuplicateMemberException;
import spring.Member;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
public class RegisterController {

    private MemberRegisterService memberRegisterService;

    public void setMemberRegisterService(
            MemberRegisterService memberRegisterService){
        this.memberRegisterService = memberRegisterService;
    }

    // register 중 step1 컨트롤러
    @RequestMapping("/register/step1") // 요청경로가  /register/step1 일 경우
     public String handleStep1(){
        return "register/step1"; // 보여줄 뷰 이름을 리턴한다.
    }

    // register 중 step2 컨트롤러
    @PostMapping("/register/step2") // post 일경우
    public String handleStep2(
            // request의 parameter들이 몇개 안될때 간단하게 구하는 어노테이션.
            // agree 요청 파라미터의 값을 읽어와서 agreeVal 파라미터에 저장한다.
            @RequestParam(value = "agree" , defaultValue = "false") Boolean agreeVal,
            Model model){
        if (!agreeVal){
            return "register/step1"; // agree 요청 파라미터의 값이 true가 아니면 "register/step1" 뷰 이름을 리턴
        }
        model.addAttribute("registerRequest", new RegisterRequest()); // <form:form> 태그를 사용하기 위한 커맨드 객체가 필요. 이를 위해 객체를 모델에 넣어주어서, 태그가 정상동작하도록 한다.
        return "register/step2"; // 요청값이 참이면 "register/step2" 뷰 이름을 리턴
    }

    // /regiseter/step2로 직접 get방식으로 접근하려고 할때
    @GetMapping("/register/step2")
    public String handlingStep2Get(){
        return "redirect:/register/step1"; // post방식이 아니라 직접 접근하려고 했으므로, step1으로 리다이렉션해준다!
    }

    // /register/step3
    @PostMapping("/register/step3")
    public String handleStep3(RegisterRequest regReq, Errors errors){ // 스프링 MVC는 handleStep3() 메서드를 호출할때 커맨드 객체와 연결된 Errors 객체를 생성해서 파라미터로 전달한다.

        // 에러탐지
        new RegisterRequestValidator().validate(regReq, errors);
        if (errors.hasErrors())
            return "register/step2";

        try{
            memberRegisterService.regist(regReq); // 회원가입
            return "register/step3"; // 성공시 Step3로
        } catch(DuplicateMemberException ex){ // 이미 동일한 이메일 주소를 가진 회원 데이터가 존재하면
            errors.rejectValue("email", "duplicate"); // error code
            return "register/step2";  // Step2로 리다엑션
        }
    }
}
