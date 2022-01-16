package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.Member;
import spring.MemberDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainForMemberDao {
    private static MemberDao memberDao;

    public static void main(String[] args){
        // AppCtx 설정을 이용하여 spring 컨테이너 생성
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppCtx.class);
        memberDao = ctx.getBean(MemberDao.class);

        selectAll();
        updateMember();
        insertMember();

        ctx.close();
    }

    public static void selectAll(){
        System.out.println("----- selectAll");
        int total = memberDao.count();
        System.out.println("전체 데이터 : " + total);
        List<Member> members = memberDao.selectAll();
        for (Member m : members) {
            System.out.println(m.getId() + ":" + m.getEmail() + ":"+m.getName());
        }
    }

    private static void updateMember(){
        System.out.println("----- update Member");
        Member member = memberDao.selectByEmail("madvirus@madvirus.net");
        String oldPw = member.getPassword();
        String newPw = Double.toHexString(Math.random());
        member.changePassword(oldPw, newPw);

        memberDao.update(member);
        System.out.println("암호 변경 :" + oldPw + ">" + newPw);
    }

    private static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("MMddHHmmss");

    private static void insertMember(){
        System.out.println("----- insertMember");

        String prefix = formatter.format(LocalDateTime.now());
        Member member = new Member(prefix + "@test.com",
                prefix, prefix, LocalDateTime.now());
        memberDao.insert(member);
        System.out.println(member.getId()+"데이터 추가");
    }
}
