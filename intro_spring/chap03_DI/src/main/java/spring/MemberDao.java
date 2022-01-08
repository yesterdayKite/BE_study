package spring;

import java.util.HashMap;
import java.util.Map;

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

}
