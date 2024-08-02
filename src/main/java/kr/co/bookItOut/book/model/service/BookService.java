package kr.co.bookItOut.book.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.book.model.dao.BookDao;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;
}
