package kr.co.bookItOut.FAQ.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.FAQ.model.dao.FaqDao;

@Service
public class FaqService {
	@Autowired
	private FaqDao faqDao;
}
