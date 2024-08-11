package kr.co.bookItOut.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardRowMapper;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.dto.MemberRowMapper;
import kr.co.bookItOut.pay.model.dto.PayRowMapper;
import kr.co.bookItOut.pay.model.dto.PayRowMapper2;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired
	private PayRowMapper payRowMapper;
	@Autowired
	private BoardRowMapper boardRowMapper;
	
	public Member selectOneMember(String memberId, String memberPw) {
		String query = "select * from member_tbl where member_id=? and member_pw=?";
		Object[] params = {memberId, memberPw};
		List list = jdbc.query(query, memberRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Member)list.get(0);
		}
	}
	public Member selectOneMember(String checkId) {
		String query = "select * from member_tbl where member_id=?";
		Object[] params = {checkId};
		List list = jdbc.query(query, memberRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Member)list.get(0);
		}
	}
	public int insertMember(Member m) {
		String query = "insert into member_tbl values(member_seq.nextval,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),?)";
		Object[] params = {m.getMemberId(),m.getMemberPw(),m.getMemberName(), m.getMemberGender(), m.getMemberAge(), m.getMemberAddr(),m.getMemberPhone(),m.getMemberImg(),m.getMemberMail()};
		int result = jdbc.update(query, params);
		return result;
	}
	
	public int updateMember(Member member, Member m, int sel) {
		if(sel==1) {
			
		String query = "update member_tbl set member_name=?, member_age=?, member_phone=?, member_img=? where member_no = ?";
		Object[] params = {m.getMemberName(),  m.getMemberAge(), m.getMemberPhone(), m.getMemberImg(), member.getMemberNo()};
		int result = jdbc.update(query, params);
		return result;
		}else{
			String query = "update member_tbl set member_name=?, member_age=?, member_phone=? where member_no = ?";
			Object[] params = {m.getMemberName(),  m.getMemberAge(), m.getMemberPhone(), member.getMemberNo()};
			int result = jdbc.update(query, params);
			return result;
		}
	}
	public Member selectSearchId(Member m) {
		String query = "select * from member_tbl where member_mail=? and member_name=?";
		Object[] params = {m.getMemberMail(), m.getMemberName()};
		List list = jdbc.query(query, memberRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Member)list.get(0);
		}
	}
	public Member selectSearchPw(Member m) {
		String query = "select * from member_tbl where member_mail=? and member_name=? and member_id=?";
		Object[] params = {m.getMemberMail(), m.getMemberName(), m.getMemberId()};
		List list = jdbc.query(query, memberRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Member)list.get(0);
		}
	}
	public List selectAllCart(int memberNo) {
		String query = "SELECT * FROM pay WHERE member_no=? ORDER BY pay_no DESC";
		Object[] params = {memberNo};
		List list = jdbc.query(query, payRowMapper, params);
		
		System.out.println(list);
		return list;
	}
	public List selectMyBoard(String memberId) {
		String query = "SELECT b.board_no,b.board_title,b.board_content,b.board_writer,b.read_count,b.reg_date FROM board b JOIN member_tbl m ON b.board_writer = m.member_id where m.MEMBER_ID=?";
		Object[] params = {memberId};
		List list = jdbc.query(query, boardRowMapper, params);
		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}
	
}
