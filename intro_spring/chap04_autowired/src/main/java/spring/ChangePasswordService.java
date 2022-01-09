package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordService {

    @Autowired // 의존을 주입하지 않아도 스프링이 @Autowired가 붙은 필드에 해당 타입의 bean 객체를 찾아서 주입한다.
    private MemberDao memberDao;

    // 비밀번호 변경
    public void changePassword(String email, String oldPwd, String newPwd){
        Member member = memberDao.selectByEmail(email); // email로 해당 멤버를 검색한다

        if (member == null) // 존재하지 않는 멤버인 경우 exception
            throw new MemberNotFoundException();

        member.changePassword(oldPwd, newPwd);

        memberDao.update(member);
    }

    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao; // ##### setter를 통해서 의존 객체를 주입받는다 #####
    }
}
