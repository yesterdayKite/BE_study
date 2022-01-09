package spring;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component // 설정클래스에 bean으로 등록하지 않아도 원하는 클래스를 빈으로 등록할 수 있게 한다.
public class MemberDao {

    private static long nexId = 0;

    private Map<String, Member> map = new HashMap<>();

    // email로 멤버 검색하기
    public Member selectByEmail(String email){
        return map.get(email);
    }

    // 멤버 삽입
    public void insert(Member member){
        member.setId(++nexId);
        map.put(member.getEmail(), member);
    }
    // 멤버 수정
    public void update(Member member){
        map.put(member.getEmail(), member);
    }

    public Collection<Member> selectAll(){
        return map.values();
    }
}
