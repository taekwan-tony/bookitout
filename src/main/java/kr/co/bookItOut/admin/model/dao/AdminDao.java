package kr.co.bookItOut.admin.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminRowMapper;

@Repository
public class AdminDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private AdminRowMapper adminRowMapper;
	public List selectAdminList(int start, int end) {
		String query = "select * from(select rownum as rnum, n.*from (select * from admin_tbl order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,adminRowMapper,params);
		return list;
	}
	public int selectAdminTotoalCount() {
		String qurey = "select count(*) from admin_tbl";
		
		int totalCount = jdbc.queryForObject(qurey,Integer.class);
		return totalCount;
	}
	public Admin selectOneMember(String memberId, String memberPw) {
		String query = "select * from admin_tbl where admin_id=? and admin_pw=?";
		Object[] params = {memberId, memberPw};
		List list = jdbc.query(query, adminRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Admin)list.get(0);
		}
	}
	
	
	
}
