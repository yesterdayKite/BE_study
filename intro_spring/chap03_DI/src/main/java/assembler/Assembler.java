package assembler;


import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberRegisterService;

/*
main 메소드에서 의존 대상 객체를 생성하고 주입하지 않고,
객체를 생성하고 주입하는 클래스를 따로 작성한다.
의존 객체 주입 = 서로 다른 두 객체를 조립한다고 생각할 수도 있기때문에,
이런 클래스를 조립기 (assembler)라고 부른다.
 */
public class Assembler {

    private MemberDao memberDao;
    private MemberRegisterService regSvc;
    private ChangePasswordService pwdSvc;

    /*
    member registerService객체와 changePasswordService객체에 대한 의존 주입한다.
    의존 객체를 변경하려면, 조립기의 코드만 수정하면 된다.
     */
    public Assembler(){
        memberDao = new MemberDao();
        regSvc = new MemberRegisterService(memberDao); // 생성자를 통해 MemberDao 객체를 주입받는다
        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao); // setter를 통해 MemberDao 객체를 주입받는다.
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    public MemberRegisterService getMemberRegisterService(){
        return regSvc;
    }

    public ChangePasswordService getChangePasswordService() {
        return pwdSvc;
    }
}
