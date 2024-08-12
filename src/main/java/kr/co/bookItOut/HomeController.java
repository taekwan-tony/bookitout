package kr.co.bookItOut;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.service.BookService;

@Controller
public class HomeController {

	@Autowired
	private BookService bookService;
	
	@GetMapping(value="/")
	public String index(Model model) {
		
		List<Book> currentBook = bookService.selectThreeBook(5);
		model.addAttribute("bList",currentBook);
		return "index";
	}
}
