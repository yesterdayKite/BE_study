package survey;

import java.util.List;

// 답변 목록을 저장한다.
public class AnsweredData {
    private List<String> responses;
    private Respondent res;

    public List<String> getResponses(){
        return responses;
    }

    public void setResponses(List<String> reponses) {
        this.responses = reponses;
    }

    public Respondent getRes(){
        return res;
    }

    public void setRes(Respondent res){
        this.res = res;
    }
}
