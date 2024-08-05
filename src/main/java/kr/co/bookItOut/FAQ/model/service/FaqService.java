package kr.co.bookItOut.FAQ.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.FAQ.model.dao.FaqDao;
import kr.co.bookItOut.FAQ.model.dto.Faq;
import kr.co.bookItOut.FAQ.model.dto.FaqListData;

@Service
public class FaqService {
	@Autowired
	private FaqDao faqDao;
	

	public FaqListData selectAllFaq(int type, int reqPage) {
		String faqType = null;
		String faqTypePage = null;
		switch(type) {
		case 1 : 
			faqType = "회원";
			faqTypePage = "회원가입/탈퇴";
			break;
		case 2 : 
			faqType = "반품";
			faqTypePage = "반품/교환/환불";
			break;
		case 3 : 
			faqType = "주문";
			faqTypePage = "주문/결제";
			break;
		case 4 : 
			faqType = "배송";
			faqTypePage = "배송/수령일 안내";
			break;
		}
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end - numPerPage +1;
		int totalCount = faqDao.totalCountBoard(faqType);
		int totalPage = 0;
		if(totalCount%numPerPage ==0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage + 1;
		}
		
		List list = faqDao.selectAllFaq(faqType,reqPage,start,end);	
		int pageNaviSize = 5;
		int pageNo = (reqPage-1)/pageNaviSize*pageNaviSize + 1;
		String pageNavi = "<div class='pagination'><ul>";
		if(pageNo !=1) {
			pageNavi += "<li>";
			pageNavi += "<a class ='page-item page-btn' href='/FAQ/faqfrm?type="+type+"&reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span><i class='fa-solid fa-angle-left'></i></span>";
			pageNavi += "</a></li>";
		}
		for(int i =0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo==reqPage) {
				pageNavi += "<a class ='page-item active-page' href='/FAQ/faqfrm?type="+type+"&reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class ='page-item' href='/FAQ/faqfrm?type="+type+"&reqPage="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class ='page-item page-btn' href='/FAQ/faqfrm?type="+type+"&reqPage="+pageNo+"'>";
			pageNavi += "<span><i class='fa-solid fa-angle-right'></i></span>";
			pageNavi += "</a></li>";
		}
		pageNavi +="</ul>";
		pageNavi +="</ul></div>";
		
		FaqListData fld = new FaqListData(list, pageNavi,faqTypePage);
		return fld;
	}

	@Transactional
	public int writeFaq(Faq f) {
		int result = faqDao.insertFaq(f);
		
		return result;
	}
}
