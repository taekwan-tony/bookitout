package kr.co.bookItOut.FAQ.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.FAQ.model.dao.FaqDao;
import kr.co.bookItOut.FAQ.model.dto.Faq;

@Service
public class FaqService {
	@Autowired
	private FaqDao faqDao;

	public List selectAllFaq(int type, int reqpage) {
		
		List list = faqDao.selectAllFaq(type,reqpage);			
		
		return list;
	}
}
