package kr.co.bookItOut.book.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.book.model.dao.BookDao;
import kr.co.bookItOut.book.model.dto.BookContent;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;

//	public int insertComment(BookContent bc) {
//		int result = bookDao.insertComment(bc);
//		return 0;
//	}
}
