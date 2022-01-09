package spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class MemberRegisterService {
    @Autowired
    private MemberDao memberDao;

    public MemberRegisterService(){}

    // 생성자를 통해 의존 객체를 전달받는다.
    // 즉, 생성자를 통해 MemberRegisterService가 의존하고 있는 MemberDao객체를 주입받은 것이다.
    public MemberRegisterService(MemberDao memberDao){
        // 주입받은 객체를 필드에 할당
        this.memberDao = memberDao;
    }

    public Long regist(RegisterRequest req){
        // 주입받은 의존 객체의 메서드를 활용
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null){
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }

        Member newMember = new Member( // 새로운 멤버 정보로 객체 생성
                req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
        memberDao.insert(newMember); // 생성된 객체 전달하여 멤버 insert
        return newMember.getId();
    }
}
