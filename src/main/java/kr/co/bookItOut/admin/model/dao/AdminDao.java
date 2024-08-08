package kr.co.bookItOut.admin.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminRowMapper;
import kr.co.bookItOut.book.model.dto.AdminBookRowMapper;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookRowMapper;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryRowMapper;

@Repository
public class AdminDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BookRowMapper bookRowMapper;
	@Autowired
	private AdminRowMapper adminRowMapper;
	@Autowired
	private AdminBookRowMapper adminBookRowMapper;
	//-판매자 리스트
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
	//--판매자리스트 끝
	
	//--로그인//
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
	//-로그인 끝
	
	//-책 리스트
	public List selectBookList(int start, int end) {
		String query = "select * from(select rownum as rnum, n.*from (select * from (select * from book left join center_inventory on (book_no = book_no2)) order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,adminBookRowMapper,params);
		return list;
	}
	
	public int selectBookTotoalCount() {
		String qurey = "select count(*) from book";
		int totalCount = jdbc.queryForObject(qurey,Integer.class);
		return totalCount;
	}
	//-책 리스트 끝
	//-책 등록//
	public int insertBook(Book book) {
		String qurey = "insert into book values(book_seq.nextval,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),?,?,?,?,?,?,?,0)";
		Object[] params = {book.getBookName(),book.getBookWriter(),
							book.getBookPrice(),book.getBookPublisher(),
							book.getPublicationDate(),book.getBookImg(),
							book.getAdminNo(),book.getBookDetailContent(),
							book.getBookDetailWriter(),book.getBookDetailImg(),
							book.getBookType(),book.getBookGenre()};
		int result = jdbc.update(qurey,params);
		return result;
	}
	
}
