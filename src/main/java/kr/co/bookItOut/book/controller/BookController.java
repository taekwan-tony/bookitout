package kr.co.bookItOut.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookComment;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.book.model.service.BookService;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryBook;

@Controller
@RequestMapping(value="/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping(value="/detail")
	public String detail(Book b, Model model) {
		Book book = bookService.selectOneBook(b);
		model.addAttribute("book", book);
		return "book/detail";
	}
	
	// 비동기 매장 위치 재고 조회
	@ResponseBody
	@GetMapping(value="/ajax1")
	public List<CenterInventoryBook> ajax1(int bookNo) {
		List<CenterInventoryBook> centerList = bookService.selectAllCenterInventory(bookNo);
		System.out.println(centerList);
		return centerList;// 출력 가능 데이터 : 재고수량, 지점명, 주소, 책이름
	}
	
	@PostMapping(value="/insertComment")
	public String insertComment(BookComment bc, Model model) {
		int result = bookService.insertComment(bc);
		if(result > 0) {
			model.addAttribute("title", "리뷰 작성 성공");
			model.addAttribute("msg", "리뷰가 작성되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "리뷰 작성 실패");
			model.addAttribute("msg", "리뷰  작성 중 에러가 발생했습니다.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc", "/book/detail?bookNo="+bc.getBookRef());
		return "common/msg";
	}

	@GetMapping(value="list")
	public String list(Model model, int reqPage) {
		BookListData bld = bookService.selectBookList(reqPage);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		return "book/list";
	}

}
