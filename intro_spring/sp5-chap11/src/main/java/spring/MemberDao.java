package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDao {

    private JdbcTemplate jdbcTemplate;

    public MemberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource); // Jdbc객체 생성
    }

    // email 주소를 사용하여 사용자 검색
    public Member selectByEmail(String email){
        List<Member> results = jdbcTemplate.query( // query를 날려서 java 객체로 가져오는 메소드 query()
                "select * from MEMBER where EMAIL = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE").toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                },
                email);
        return results.isEmpty()?null:results.get(0); // query()는 쿼리 실해결과 존재하지 않으면 길이가 0인 List 리턴
    }

    public void insert(final Member member){
        KeyHolder keyHolder = new GeneratedKeyHolder(); // GeneratedKeyHolder는 자동생성된 키값을 구해주는 KeyHolder 구현 클래스이다.
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into MEMBER(EMAIL, PASSWORD, NAME, REGDATE)" +
                                "values (?, ?, ?, ?)",
                        new String[]{"ID"}); // 자동새엇ㅇ되는 키 칼럼 목록을 지정할때 사용한다. (Member테이블은 ID칼럼이 자동증가 키 칼럼이므로, 두번째 파라미터값으로 {"ID"}값을 주었다.)
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getName());
                pstmt.setTimestamp(4,
                        Timestamp.valueOf(member.getRegisterDateTime()));
                return pstmt;
            }
        }, keyHolder); // 자동생성된 key값을 keyHolder에 보관한다.
        Number keyValue = keyHolder.getKey(); // key값은 getKey()를 사용하여 구함
        member.setId(keyValue.longValue()); // long타입으로 저장함
    }

    // 정보 업데이트
    public void update(Member member){
        jdbcTemplate.update(
                "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail());
    }

    // 모든 정보 출력
    public List<Member> selectAll(){
        List<Member> results = jdbcTemplate.query("select * from MEMBER",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE").toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                });
        return results;
    }

    // MEMBER 테이블의 전체 행 갯수를 구한다.
    public int count(){
        Integer count = jdbcTemplate.queryForObject( // query()와 다르게 queryForObject()는 반드시 결과가 한 행이어야한다.
                "select count(*) from MEMBER", Integer.class);
        return count;
    }
}