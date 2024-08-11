package kr.co.bookItOut.admin.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.admin.model.dao.AdminDao;
import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.admin.model.dto.OrderBookListData;
import kr.co.bookItOut.book.model.dto.AdminBook;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventory;
import kr.co.bookItOut.member.model.dto.Member;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public AdminListData selectAdminList(int rePage , Admin admin) {
		
		int numperPage=7;
		int end = rePage*numperPage;
		int start = end - numperPage+1;
		List list = new ArrayList<AdminListData>();
		
		if(admin.getAdminType() == 1) {
			list = adminDao.selectAdminList1(start,end); 
			int totalCount = adminDao.selectAdminTotoalCount();
			
			int totalPage = 0;
			if(totalCount%numperPage == 0) {
				totalPage = totalCount/numperPage;
			}else {
				totalPage = totalCount/numperPage+1;
			}
			
			int pagNavSize = 3;
			
			int pageNo = ((rePage-1)/pagNavSize)*pagNavSize+1;
			
			String pageNavi = "<div class = 'pagination'> <ul>";
			
			if(pageNo != 1) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/admin/adminIndex?rePage="+pageNo+"'>";
				pageNavi += "<span><i class='fa-solid fa-angle-left'></i></span>";
				pageNavi += "</a></li>";
			}
			
			for(int i=0;i<pagNavSize;i++) {
				pageNavi +="<li>";
				if(pageNo == rePage) {
					pageNavi += "<a class='page-item active-page' href='/admin/adminIndex?rePage="+pageNo+"'>";
				}else {
					pageNavi +="<a class='page-item' href='/admin/adminIndex?rePage="+pageNo+"'>";
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
				pageNavi += "<a class='page-item' href='/admin/adminIndex?rePage="+pageNo+"'>";
				pageNavi += "<span><i class='fa-solid fa-angle-right'></i></span>";
				pageNavi += "</a></li>";
				
			}
			pageNavi += "</ul></div>";
			 // pageNavi 오타
			AdminListData ald = new AdminListData(list,pageNavi);
			
			return ald;
		}else {
			list = adminDao.selectAdminList2(admin);
			System.out.println(list);
			AdminListData ald = new AdminListData();
			ald.setList(list);
			return ald;
		}
		
		
		
	}
	
	//--------------회원 처리---------------------------------------------------------------------------

	public Admin selectOneMember(String memberId, String memberPw) {
		Admin admin = adminDao.selectOneMember(memberId, memberPw);
		return admin;
	}
	
//---------book 서비스--------------------------------------------------------------------------------
	//전체 책 리스트
	public BookListData selectbookList(int rePage) {
		List list = new ArrayList<BookListData>();
	
	int numperPage=10;
	int end = rePage*numperPage;
	int start = end - numperPage+1;
	
	list = adminDao.selectBookList(start, end);
		
	
	int totalCount = adminDao.selectBookTotoalCount();
	
	int totalPage = 0;
	if(totalCount%numperPage == 0) {
		totalPage = totalCount/numperPage;
	}else {
		totalPage = totalCount/numperPage+1;
	}
	
	int pagNavSize = 3;
	
	int pageNo = ((rePage-1)/pagNavSize)*pagNavSize+1;
	
	String pageNavi = "<div class = 'pagination'> <ul>";
	
	if(pageNo != 1) {
		pageNavi += "<li>";
		pageNavi += "<a class='page-item' href='/admin/bookListFrm?rePage="+pageNo+"'>";
		pageNavi += "<span><i class='fa-solid fa-angle-left'></i></span>";
		pageNavi += "</a></li>";
	}
	
	for(int i=0;i<pagNavSize;i++) {
		pageNavi +="<li>";
		if(pageNo == rePage) {
			pageNavi += "<a class='page-item active-page' href='/admin/bookListFrm?rePage="+pageNo+"'>";
		}else {
			pageNavi +="<a class='page-item' href='/admin/bookListFrm?rePage="+pageNo+"'>";
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
		pageNavi += "<a class='page-item' href='/admin/bookListFrm?rePage="+pageNo+"'>";
		pageNavi += "<span><i class='fa-solid fa-angle-right'></i></span>";
		pageNavi += "</a></li>";
		
	}
	pageNavi += "</ul></div>";
	 // pageNavi 오타
	BookListData bld = new BookListData(list,pageNavi);
	
	return bld;
}
	
	
	//가맹점에 있는 책 리스트 
public OrderBookListData selectAdminBookList(int rePage,Book book, Admin admin) {
			List list = new ArrayList<OrderBookListData>();
		
		int numperPage=10;
		int end = rePage*numperPage;
		int start = end - numperPage+1;

		list = adminDao.selectBookList2(start,end,book,admin); //관리자번호 가져와서/ 책번호
			
			
		
		int totalCount = adminDao.selectBookTotoalCount();
		
		int totalPage = 0;
		if(totalCount%numperPage == 0) {
			totalPage = totalCount/numperPage;
		}else {
			totalPage = totalCount/numperPage+1;
		}
		
		int pagNavSize = 3;
		
		int pageNo = ((rePage-1)/pagNavSize)*pagNavSize+1;
		
		String pageNavi = "<div class = 'pagination'> <ul>";
		
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/admin/orderAdmin2?rePage="+pageNo+"'>";
			pageNavi += "<span><i class='fa-solid fa-angle-left'></i></span>";
			pageNavi += "</a></li>";
		}
		
		for(int i=0;i<pagNavSize;i++) {
			pageNavi +="<li>";
			if(pageNo == rePage) {
				pageNavi += "<a class='page-item active-page' href='/admin/orderAdmin2?rePage="+pageNo+"'>";
			}else {
				pageNavi +="<a class='page-item' href='/admin/orderAdmin2?rePage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/admin/orderAdmin2?rePage="+pageNo+"'>";
			pageNavi += "<span><i class='fa-solid fa-angle-right'></i></span>";
			pageNavi += "</a></li>";
			
		}
		pageNavi += "</ul></div>";
		 // pageNavi 오타
		OrderBookListData obld = new OrderBookListData(list,pageNavi);
		
		return obld;
	}


//북 등록
@Transactional //변화 있을때 커밋 롤백
public int insertBook(Book book) {
	 int result = adminDao.insertBook(book);
	
	return result;
}

//삭제--

public Book deleteBook(int bookNo) {
	Book book = adminDao.selectAdminFile(bookNo);
	
	int result = adminDao.deleteBook(bookNo);
	if(result>0) {
		return book;
	}
	return null;
}
//수정 --
@Transactional
public int updatebookList(Book book) {
	
	int result =adminDao.updateBook(book);
	if(result>0) {
		return result;
	}
	return 0;
}

public Book selectOneBook(int bookNo) {
	Book b = adminDao.selectbook(bookNo);
	
	
	return b;
}

@Transactional
public int updateDetailBook(Book b) {
	int result = adminDao.updateDetailBook(b);
	if(result>0){
		return result;
	}
	
	return 0;
}

public int insertOrderAdmin(CenterInventory centerInventory, int orderBookCount) {
	CenterInventory c = adminDao.selectCenterInventory(centerInventory);
	int result = adminDao.inserOrderAdmin(c,orderBookCount);
	if(result>0) {
		return result;
	}
	return 0;
}














}
