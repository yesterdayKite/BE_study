package survey;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/survey") // /survey로 들어오는 request에게 매핑한다
public class SurveyController {

    @GetMapping //GET일때
    public String form(Model model){
        List<Question> questions = createQuestions();
        model.addAttribute("questions", questions);
        return "survey/surveyForm";
    }

    private List<Question> createQuestions() {
        Question q1 = new Question("당신의 역할은 무엇입니까?",
                Arrays.asList("server", "front", "full stack"));
        Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
                Arrays.asList("eclipse", "intelliJ", "sublime"));
        Question q3 = new Question("하고 싶은 말을 적어주세요.");
        return Arrays.asList(q1, q2, q3);
    }

    @PostMapping //POST일때
    public String submit(@ModelAttribute("ansData") AnsweredData data){
        return "survey/submitted";
    }
}
