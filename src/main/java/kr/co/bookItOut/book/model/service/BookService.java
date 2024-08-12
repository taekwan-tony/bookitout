package kr.co.bookItOut.book.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.book.model.dao.BookDao;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookComment;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.book.model.dto.CenterMap;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryBook;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;

	public Book selectOneBook(Book book1) {
		Book book = bookDao.selectOneBook(book1);
		return book;
	}

	public List<CenterInventoryBook> selectAllCenterInventory(int bookNo) {
		List<CenterInventoryBook> centerList = bookDao.selectAllCenterInventory(bookNo);
		return centerList;
	}

	@Transactional
	public Book selectOneBook(int bookNo, String check, int memberNo) {
		Book b = bookDao.selectOneBook(bookNo);
		if(b != null) {
			if(check == null) {
				int result = bookDao.updateReadCount(bookNo);
			}
			// 댓글 조회
			List<BookComment> commentList = bookDao.selectCommentList(bookNo, memberNo);
			b.setCommentList(commentList);
			// 대댓글 조회
			List reCommentList = bookDao.selectReCommentList(bookNo, memberNo);
			b.setReCommentList(reCommentList);
		}
		return b;
	}

	@Transactional
	public int insertComment(BookComment bc) {
		int result = bookDao.insertComment(bc);
		return result;
	}

	@Transactional
	public int updateComment(BookComment bc) {
		int result = bookDao.updateComment(bc);
		return result;
	}

	@Transactional
	public int deleteComment(BookComment bc) {
		int result = bookDao.deleteComment(bc);
		return result;
	}

	@Transactional
	public int likePush(int bookCommentNo, int isLike, int memberNo) {
		int result = 0;
		if(isLike == 0) {
			// 현재 좋아요를 누르지 않은 상태에서 클릭 -> 좋아요 -> insert
			result = bookDao.insertBookCommentLike(bookCommentNo, memberNo);
		}else if(isLike == 1) {
			// 현재 좋아요를 누른 상태에서 클릭 -> 좋아요 취소 -> delete
			result = bookDao.deleteBookCommentLike(bookCommentNo, memberNo);
		}
		if(result > 0) {
			// 좋아요, 좋아요 취소 로직을 수행하고나면 현재 좋아요 갯수를 조회해서 리턴
			int likeCount = bookDao.selectBookCommentLikeCount(bookCommentNo);
			return likeCount;
		}else {			
			return -1;
		}
	}

//	public CenterMap selectCenterMap(CenterMap cm) {
//		CenterMap list = bookDao.selectCenterMap(cm);
//		return list;
//	}

	public List<CenterMap> selectOneMap(int adminNo) {
		List<CenterMap> centerMap = bookDao.selectOneMap(adminNo);
		return centerMap;
	}

	public BookListData selectBookList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage +1;
		
		List list = bookDao.selectBookList(start, end);
				
		int totalCount = bookDao.selectBookTotalCount();
		int totalPage = 0;
		if(totalCount % numPerPage == 0) {
			totalPage = totalCount / numPerPage;			
		}else {
			totalPage = totalCount / numPerPage + 1;			
		}
		
		int pageNaviSize = 10;		
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		
		String pageNavi = "<div class='inner'><ul>";
		if(pageNo !=1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='book/list?reqPage=" + (pageNo - 1) +"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";			
		}
		
		for(int i = 0; i< pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/book/list?reqPage=" + pageNo + "'>"; 
			} else {
				pageNavi += "<a class='page-item' href='/book/list?reqPage=" +pageNo + "'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			
			if(pageNo > totalPage) {
				break;
			}			
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/book/list?reqPage=" + pageNo + "'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul></div>";
		
		BookListData bld = new BookListData(list, pageNavi);		
		
		return bld;
	}

}
