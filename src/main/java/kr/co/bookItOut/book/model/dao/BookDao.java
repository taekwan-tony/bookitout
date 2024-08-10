package kr.co.bookItOut.book.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookComment;
import kr.co.bookItOut.book.model.dto.BookCommentRowMapper;
import kr.co.bookItOut.book.model.dto.BookRowMapper;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryBookRowMapper;


@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private BookRowMapper bookRowMapper;
	
	@Autowired
	private CenterInventoryBookRowMapper centerInventoryBookRowMapper;
	
	@Autowired
	private BookCommentRowMapper bookCommentRowMapper;

	public Book selectOneBook(Book b) {
		String query = "select * from book where book_no = ?";
		Object[] params = {b.getBookNo()};
		List list = jdbc.query(query, bookRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Book)list.get(0);
		}
	}

	// 판매점 위치 ajax 비동기처리
	public List selectAllCenterInventory(int bookNo) {
		String query = "select admin_name, admin_addr, center_book_count from admin_tbl join center_inventory using (admin_no) join book on (book_no = book_no2) where book_no = ? and admin_type = 2";
		Object[] params = {bookNo};
		List centerList = jdbc.query(query, centerInventoryBookRowMapper, params);
		System.out.println(centerList.size());
		return centerList;
	}

	public int insertComment(BookComment bc) {
		String query = "insert into book_comment values(book_comment_seq.nextval, ?, ?, to_char(sysdate, 'yyyy-mm-dd'), ?, null)";
		String bookCommentRef = bc.getBookCommentRef() == 0 ? null : String.valueOf(bc.getBookCommentRef());
		Object[] params = {bc.getBookCommentWriter(), bc.getBookCommentContent(), bc.getBookRef(), bookCommentRef};
		int result = jdbc.update(query, params);
		return result;
	}

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

	public List selectGenreOneBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreOneBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreOnePublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreOneEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreOneBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreOneBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreTwoBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '문학' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreTwoBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '문학' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreTwoPublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '문학' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreTwoEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '문학' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreTwoBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '문학' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreTwoBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '문학' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreThreeBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '재테크' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreThreeBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '재테크' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreThreePublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '재테크' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreThreeEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '재테크' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreThreeBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '재테크' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreThreeBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '재테크' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFourBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '기타' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFourBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '기타' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFourPublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '기타' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFourEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '기타' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFourBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '기타' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFourBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '국내도서' and book_genre = '기타' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		
		return list;
	}

	public List selectGenreFiveBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		
		return list;
	}

	public List selectGenreFivePublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveTwoBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '문학' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveTwoBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '문학' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);		
		return list;
	}

	public List selectGenreFiveTwoPublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '문학' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveTwoEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '문학' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveTwoBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '문학' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveTwoBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '문학' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		
		return list;
	}

	public List selectGenreFiveThreeBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '재테크' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);		
		return list;
	}

	public List selectGenreFiveThreeBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '재테크' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveThreePublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '재테크' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		
		return list;
	}

	public List selectGenreFiveThreeEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '재테크' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveThreeBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '재테크' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveThreeBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '재테크' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveFourBookNoList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '기타' order by book_no desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveFourBookNameList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '기타' order by book_name)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveFourPublicationDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '기타' order by publication_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);		
		return list;
	}

	public List selectGenreFiveFourEnrollDateList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '기타' order by enroll_date desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveFourBookPriceList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '기타' order by book_price)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}

	public List selectGenreFiveFourBookPriceDescList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from(select * from book where book_type = '해외도서' and book_genre = '기타' order by book_price desc)b) where rnum between ? and ?";
		Object[] params = {start, end};
		List list = jdbc.query(query, bookRowMapper, params);
		return list;
	}
	
	public Book selectOneBook(int bookNo) {
		String query = "select * from book where book_no = ?";
		Object[] params = {bookNo};
		List list = jdbc.query(query, bookRowMapper, params);
		if(list .isEmpty()) {
			return null;
		} else {
			return (Book)list.get(0);			
		}
	}

	public int updateReadCount(int bookNo) {
		String query = "update book set read_count = read_count+1 where book_no = ?";
		Object[] params = {bookNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public List<BookComment> selectCommentList(int bookNo, int memberNo) {
		String query = "SELECT BC.* ,\r\n" + 
				"(SELECT COUNT(*) FROM BOOK_COMMENT_THUMB WHERE BOOK_COMMENT_NO = BC.BOOK_COMMENT_NO) AS LIKE_COUNT,\r\n" + 
				"(SELECT COUNT(*) FROM BOOK_COMMENT_THUMB WHERE BOOK_COMMENT_NO = BC.BOOK_COMMENT_NO AND MEMBER_NO = ?) AS IS_LIKE\r\n" + 
				"FROM BOOK_COMMENT BC\r\n" + 
				"WHERE BOOK_REF = ? AND BOOK_COMMENT_REF IS NULL\r\n" + 
				"ORDER BY 1";
		Object[] params = {memberNo, bookNo};
		List list = jdbc.query(query, bookCommentRowMapper, params);
		return list;
	}

	public List selectReCommentList(int bookNo, int memberNo) {
		String query = "SELECT BC.* ,\r\n" + 
				"(SELECT COUNT(*) FROM BOOK_COMMENT_THUMB WHERE BOOK_COMMENT_NO = BC.BOOK_COMMENT_NO) AS LIKE_COUNT,\r\n" + 
				"(SELECT COUNT(*) FROM BOOK_COMMENT_THUMB WHERE BOOK_COMMENT_NO = BC.BOOK_COMMENT_NO AND MEMBER_NO = ?) AS IS_LIKE\r\n" + 
				"FROM BOOK_COMMENT BC\r\n" + 
				"WHERE BOOK_REF = ? AND BOOK_COMMENT_REF IS NOT NULL\r\n" + 
				"ORDER BY 1";
		Object[] params = {memberNo, bookNo};
		List list = jdbc.query(query, bookCommentRowMapper, params);
		return list;
	}

	public int insertBookCommentLike(int bookCommentNo, int memberNo) {
		String query = "insert into book_comment_thumb values(?, ?)";
		Object[] params = {bookCommentNo, memberNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteBookCommentLike(int bookCommentNo, int memberNo) {
		String query = "delete from book_comment_thumb where book_comment_no = ? and member_no = ?";
		Object[] params = {bookCommentNo, memberNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectBookCommentLikeCount(int bookCommentNo) {
		String query = "select count(*) from book_comment_thumb where book_comment_no = ?";
		Object[] params = {bookCommentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}
}







