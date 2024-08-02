package kr.co.bookItOut.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/book")
public class BookController {
	@GetMapping(value="/detail")
	private String detail() {
		return "book/detail";
	}
}
