package kr.co.bookItOut.book.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.book.model.dto.BookContent;
import kr.co.bookItOut.book.model.dto.BookRowMapper;

@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private BookRowMapper bookRowMapper;

//	public int insertComment(BookContent bc) {
//		String query = "insert into book_content values(, , , , to_char(sysdate, 'yyyy-mm-dd'))";
////		String boardComment
//		return 0;
//	}
}
