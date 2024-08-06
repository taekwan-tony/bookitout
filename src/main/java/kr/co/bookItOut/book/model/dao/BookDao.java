package kr.co.bookItOut.book.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.book.model.dto.BookRowMapper;


@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private BookRowMapper bookRowMapper;

	public List selectBookList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book order by 1 desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query,bookRowMapper, params);		
		
		return list;
	}

	public int selectBookTotalCount() {
		String query = "select count(*) from book";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
}
