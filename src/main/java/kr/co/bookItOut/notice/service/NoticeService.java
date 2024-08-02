package kr.co.bookItOut.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.notice.dao.NoticeDao;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	
}
