package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {

    private MemberDao memberDao;

    // 비밀번호 변경
    @Transactional // 여러 쿼리를 원자적으로 실행하기 위함
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
