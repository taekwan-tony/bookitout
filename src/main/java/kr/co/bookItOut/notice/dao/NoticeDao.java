package kr.co.bookItOut.notice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.notice.dto.NoticeRowMapper;

@Repository
public class NoticeDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private NoticeRowMapper noticeRowMapper;
	
	public List selectAllNotice(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from notice order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, noticeRowMapper,params);
		return list;
	}

	public int totalCount() {
		String query = "select count(*) from notice";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	
	
}
