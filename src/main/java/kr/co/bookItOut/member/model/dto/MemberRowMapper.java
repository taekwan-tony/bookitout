package kr.co.bookItOut.member.model.dto;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberRowMapper implements RowMapper<Member>{

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member m = new Member();
		m.setEnrollDate(rs.getString("enroll_date"));
		m.setMemberAddr(rs.getString("member_addr"));
		m.setMemberAge(rs.getInt("member_age"));
		m.setMemberGender(rs.getString("member_gender"));
		m.setMemberId(rs.getString("member_id"));
		m.setMemberImg(rs.getString("member_img"));
		m.setMemberName(rs.getString("member_name"));
		m.setMemberNo(rs.getInt("member_no"));
		m.setMemberPhone(rs.getString("member_phone"));
		m.setMemberPw(rs.getString("member_pw"));
		
		return m;
	}

		
}
