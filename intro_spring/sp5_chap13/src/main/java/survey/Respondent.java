package survey;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;

// Respondent 클래스는 응답자 정보를 담는다.
public class Respondent {

    private int age;
    private String location;

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getLoation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
}
