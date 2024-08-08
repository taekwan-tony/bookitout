package kr.co.bookItOut.book.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.book.model.dto.Book;
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

//	public int insertComment(BookContent bc) {
//	insert into book values(book_seq.nextval, '당신이 누군가를 죽였다', '히가시노 게이고 저자', 19800, '북다', '2024-07-23', to_char(sysdate, 'yyyy-mm-dd'), 'book-sample.jpg', 24, '히가시노 게이고가 재현한 황금시대 본격 미스터리 히가시노 게이고 101번째 작품에서 미스터리의 원점으로! “미스터리란 어떤 소설인가? 라는 질문을 들었을 때 이런 소설이다, 라고 대답할 수 있는 작품입니다.”', '(東野圭吾) 오늘의 일본을 대표하는 작가. 1958년 오사카 출생. 오사카 부립대학 졸업 후 엔지니어로 일하며 틈틈이 소설을 쓰다 마침내 전업 작가의 길로 들어섰다. 1985년 『방과 후』로 제31회 에도가와 란포상을 수상하며 데뷔. 1999년 『비밀』로 제52회 일본추리작가협회상, 2006년 『용의자 X의 헌신』으로 제134회 나오키상과 제6회 본격미스터리대상 소설부문상, 2012년 『나미야 잡화점의 기적』으로 제7회 중앙공론문예상, 2013년 『몽환화』로 제26회 시바타렌자부로상, 2014년 『기도의 막이 내릴 때』로 제48회 요시카와에이지 문학상을 수상했다. 그 밖의 작품으로는 『백야행』 『공허한 십자가』 『라플라스의 마녀』 『가면산장 살인사건』 『악의』 『방황하는 칼날』 『녹나무의 파수꾼』 『블랙 쇼맨과 운명의 바퀴』 등이 있다.', 'book-detail.jpg', '국내도서', '문학', 0);

//		String query = "insert into book_content values(book_seq.nextval , , , , to_char(sysdate, 'yyyy-mm-dd'))";
////		String boardComment
//		return 0;
//	}

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
