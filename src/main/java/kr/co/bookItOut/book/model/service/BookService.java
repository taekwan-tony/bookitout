package kr.co.bookItOut.book.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.bookItOut.book.model.dao.BookDao;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookComment;
import kr.co.bookItOut.book.model.dto.BookListData;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;

	public Book selectOneBook(Book b) {
		Book book = bookDao.selectOneBook(b);
		return book;
	}

	public List selectAllCenterInventory(int bookNo) {
		List centerList = bookDao.selectAllCenterInventory(bookNo);
		return centerList;
	}

//	public int insertComment(BookComment bc) {
//		int result = bookDao.insertComment(bc);
//		return result;
//	}

	public BookListData selectBookList(int reqPage, int type) {
		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage +1;
		
		//List list = bookDao.selectBookList(start, end);
		List list = new ArrayList<List>();
		if(type==1) {
			list = bookDao.selectBookNoList(start, end);
		}
		if(type==2) {
			list = bookDao.selectBookNameList(start, end);
		}
		if(type==3) {
			list = bookDao.selectPublicationDateList(start, end);
		}
		if(type==4) {
			list = bookDao.selectEnrollDateList(start, end);
		}
		if(type==5) {
			list = bookDao.selectBookPriceList (start, end);
		}
		if(type==6) {
			list = bookDao.selectBookPriceDescList(start, end);
		}
		
		
		
				
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
			pageNavi += "<a class='page-item' href='book/list?reqPage=" + (pageNo - 1) +"&type="+type+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";			
		}
		
		for(int i = 0; i< pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/book/list?reqPage=" + pageNo + "&type="+type+"'>"; 
			} else {
				pageNavi += "<a class='page-item' href='/book/list?reqPage=" +pageNo + "&type="+type+"'>";
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
			pageNavi += "<a class='page-item' href='/book/list?reqPage=" + pageNo + "&type="+type+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul></div>";
		
		BookListData bld = new BookListData(list, pageNavi, type);		
		
		return bld;
	}

}
