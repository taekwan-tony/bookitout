package kr.co.bookItOut.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.book.model.dto.BookContent;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.book.model.service.BookService;

@Controller
@RequestMapping(value="/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping(value="/detail")
	public String detail() {
		return "book/detail";
	}
	
//	@PostMapping(value="/insertComment")
//	public String insertComment(BookContent bc) {
//		int result = bookService.insertComment(bc);
//		return null;
//	}

	@GetMapping(value="list")
	public String list(Model model, int reqPage) {
		BookListData bld = bookService.selectBookList(reqPage);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		return "book/list";  
	}

}
