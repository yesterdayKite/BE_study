package spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MemberPrinter {
    private DateTimeFormatter dateTimeFormatter;

    public void print(Member member){
        if (dateTimeFormatter == null){
            System.out.printf(
                    "회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n",
                    member.getId(), member.getEmail(),member.getName(), member.getRegisterDateTime());
        } else {
            System.out.printf(
                    "회원정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n",
                    member.getId(), member.getEmail(),member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
        }
    }

    @Autowired(required = false) // 매칭되는 빈이 없어도 exception 발생하지 않는다.
    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }
}
