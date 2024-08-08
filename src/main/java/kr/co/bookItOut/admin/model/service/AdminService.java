package kr.co.bookItOut.admin.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.admin.model.dao.AdminDao;
import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.member.model.dto.Member;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public AdminListData selectAdminList(int rePage) {
		int numperPage=7;
		int end = rePage*numperPage;
		int start = end - numperPage+1;
		List list = adminDao.selectAdminList(start,end); 
		
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
	}
	
	//--------------회원 처리---------------------------------------------------------------------------

	public Admin selectOneMember(String memberId, String memberPw) {
		Admin admin = adminDao.selectOneMember(memberId, memberPw);
		return admin;
	}
	
//---------book 서비스--------------------------------------------------------------------------------
	
public BookListData selectbookList(int rePage) {
		
		int numperPage=10;
		int end = rePage*numperPage;
		int start = end - numperPage+1;
		List list = adminDao.selectBookList(start,end); 
		
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

//북 등록
@Transactional //변화 있을때 커밋 롤백
public int insertBook(Book book) {
	 int result = adminDao.insertBook(book);
	
	return result;
}
@Transactional
public int deleteBook(int bookNo) {
	int result = adminDao.deleteBook(bookNo);
	return result;
}




}
