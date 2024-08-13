package kr.co.bookItOut.book.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.admin.model.dto.Admin;
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

	public Admin selectOneAdmin(Admin admin, int adminNo) {
		Admin adm = bookDao.selectOneAdmin(admin, adminNo);
		return adm;
	}
	
	// 국내목록
	public BookListData selectBookList(int reqPage, int type, int genre) {
		int numPerPage = 5;

		int end = reqPage * numPerPage;
		int start = end - numPerPage +1;
		
		//List list = bookDao.selectBookList(start, end);
		List list = new ArrayList<List>();
		if(genre ==1) {
			if(type==1) {
				list = bookDao.selectGenreOneBookNoList(start, end);
			}else if(type==2) {
				list = bookDao.selectGenreOneBookNameList(start, end);
			}else if(type==3) {
				list = bookDao.selectGenreOnePublicationDateList(start, end);
			}else if(type==4) {
				list = bookDao.selectGenreOneEnrollDateList(start, end);
			}else if(type==5) {
				list = bookDao.selectGenreOneBookPriceList (start, end);
			}else if(type==6) {
				list = bookDao.selectGenreOneBookPriceDescList(start, end);
			}
		}else if (genre==2) {
			if(type==1) {
				list = bookDao.selectGenreTwoBookNoList(start, end);
			}else if(type==2) {
				list = bookDao.selectGenreTwoBookNameList(start, end);
			}else if(type==3) {
				list = bookDao.selectGenreTwoPublicationDateList(start, end);
			}else if(type==4) {
				list = bookDao.selectGenreTwoEnrollDateList(start, end);
			}else if(type==5) {
				list = bookDao.selectGenreTwoBookPriceList (start, end);
			}else if(type==6) {
				list = bookDao.selectGenreTwoBookPriceDescList(start, end);
			}
		}else if (genre==3) {
			if(type==1) {
				list = bookDao.selectGenreThreeBookNoList(start, end);
			}else if(type==2) {
				list = bookDao.selectGenreThreeBookNameList(start, end);
			}else if(type==3) {
				list = bookDao.selectGenreThreePublicationDateList(start, end);
			}else if(type==4) {
				list = bookDao.selectGenreThreeEnrollDateList(start, end);
			}else if(type==5) {
				list = bookDao.selectGenreThreeBookPriceList (start, end);
			}else if(type==6) {
				list = bookDao.selectGenreThreeBookPriceDescList(start, end);
			}
		}else if (genre==4) {
			if(type==1) {
				list = bookDao.selectGenreFourBookNoList(start, end);
			}else if(type==2) {
				list = bookDao.selectGenreFourBookNameList(start, end);
			}else if(type==3) {
				list = bookDao.selectGenreFourPublicationDateList(start, end);
			}else if(type==4) {
				list = bookDao.selectGenreFourEnrollDateList(start, end);
			}else if(type==5) {
				list = bookDao.selectGenreFourBookPriceList (start, end);
			}else if(type==6) {
				list = bookDao.selectGenreFourBookPriceDescList(start, end);
			}
		}
		
		//
				
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
			
			pageNavi += "<a class='page-item' href='book/list?reqPage=" + (pageNo - 1) +"&type= "+type+" &genre= "+genre+"'>";
			
			/*
			if(genre==1) {
				
				pageNavi += "<a class='page-item' href='book/list?reqPage=" + (pageNo - 1) +"&type= "+type+" &genre= "+genre+"'>";
			}else if(genre==5) {
				pageNavi += "<a class='page-item' href='book/listFore?reqPage=" +(pageNo-1)+"&type="+type+"&genre="+genre+"'>";
			}
			*/
			
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";			
		}
		
		for(int i = 0; i< pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				
					pageNavi += "<a class='page-item active-page' href='/book/list?reqPage=" + pageNo + "&type="+type+"&genre= "+genre+"'>";
				
				 
			} else {
				
					pageNavi += "<a class='page-item' href='/book/list?reqPage=" +pageNo + "&type="+type+"&genre= "+genre+"'>";
				
				
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
			
				pageNavi += "<a class='page-item' href='/book/list?reqPage=" + pageNo + "&type="+type+"&genre= "+genre+"'>";
			
			
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul></div>";
		
		BookListData bld = new BookListData(list, pageNavi);		
		
		return bld;
	}

	// 해외목록
	public BookListData selectBookListFore(int reqPage, int type, int genre) {
		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage +1;
		
		List list = new ArrayList<List>();
		if(genre==5) {
			if(type==1) {
				list = bookDao.selectGenreFiveBookNoList(start, end);
			}else if (type==2) {
				list = bookDao.selectGenreFiveBookNameList(start, end);
			}else if (type==3) {
				list = bookDao.selectGenreFivePublicationDateList(start, end);
			}else if (type==4) {
				list = bookDao.selectGenreFiveEnrollDateList(start, end);
			}else if (type==5) {
				list = bookDao.selectGenreFiveBookPriceList(start, end);
			}else if (type==6) {
				list = bookDao.selectGenreFiveBookPriceDescList(start, end);
			}
		}   else if (genre==2) {
			if(type==1) {
				list = bookDao.selectGenreFiveTwoBookNoList(start, end);
			}else if (type==2) {
				list = bookDao.selectGenreFiveTwoBookNameList(start, end);
			}else if (type==3) {
				list = bookDao.selectGenreFiveTwoPublicationDateList(start, end);
			}else if (type==4) {
				list = bookDao.selectGenreFiveTwoEnrollDateList(start, end);
			}else if (type==5) {
				list = bookDao.selectGenreFiveTwoBookPriceList(start, end);
			}else if (type==6) {
				list = bookDao.selectGenreFiveTwoBookPriceDescList(start, end);
			}
		}else if (genre==3) {
			if(type==1) {
				list = bookDao.selectGenreFiveThreeBookNoList(start, end);
			}else if (type==2) {
				list = bookDao.selectGenreFiveThreeBookNameList(start, end);
			}else if (type==3) {
				list = bookDao.selectGenreFiveThreePublicationDateList(start, end);
			}else if (type==4) {
				list = bookDao.selectGenreFiveThreeEnrollDateList(start, end);
			}else if (type==5) {
				list = bookDao.selectGenreFiveThreeBookPriceList(start, end);
			}else if (type==6) {
				list = bookDao.selectGenreFiveThreeBookPriceDescList(start, end);
			}
		}else if(genre==4) {
			if(type==1) {
				list = bookDao.selectGenreFiveFourBookNoList(start, end);
			}else if (type==2) {
				list = bookDao.selectGenreFiveFourBookNameList(start, end);
			}else if (type==3) {
				list = bookDao.selectGenreFiveFourPublicationDateList(start, end);
			}else if (type==4) {
				list = bookDao.selectGenreFiveFourEnrollDateList(start, end);
			}else if (type==5) {
				list = bookDao.selectGenreFiveFourBookPriceList(start, end);
			}else if (type==6) {
				list = bookDao.selectGenreFiveFourBookPriceDescList(start, end);
			}
		}
		
		int totalCount = bookDao.selectBookTotalCount();
		int totalPage =0;
		if(totalCount % numPerPage ==0) {
			totalPage = totalCount / numPerPage;
			
		}else {
			totalPage = totalCount / numPerPage +1;			
		}
		
		int pageNaviSize=10;
		int pageNo = ((reqPage -1)/ pageNaviSize) * pageNaviSize +1;
		String pageNavi = "<div class='inner'><ul>";
		if(pageNo !=1) {
			pageNavi += "<li>";			
			pageNavi += "<a class='page-item' href='book/listFore?reqPage=" + (pageNo - 1) +"&type= "+type+" &genre= "+genre+"'>";						
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";			
		}
		
		for(int i = 0; i< pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {			
				pageNavi += "<a class='page-item active-page' href='/book/listFore?reqPage=" + pageNo + "&type="+type+"&genre= "+genre+"'>";			
			} else {
					pageNavi += "<a class='page-item' href='/book/listFore?reqPage=" +pageNo + "&type="+type+"&genre= "+genre+"'>";			
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
				pageNavi += "<a class='page-item' href='/book/listFore?reqPage=" + pageNo + "&type="+type+"&genre= "+genre+"'>";	
			
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul></div>";
		
		BookListData bld = new BookListData(list, pageNavi);		
		
		return bld;
	}

	public List<Book> selectThreeBook() {
		List<Book> bList = bookDao.selectThreeBook();
		
		return bList;
	}
	public List<Book> selectFiveBook() {
		List<Book> bList = bookDao.selectFiveBook();
		
		return bList;
	}

	public int selectBookCommentCount(int bookNo) {
		int commentCount=bookDao.selectBookCommentCount(bookNo);
		return commentCount;
	}

}









