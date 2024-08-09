package kr.co.bookItOut.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value="/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@Value("${file.root}")
	private String root;

	@Autowired
	private FileUtils fileUtils;
	
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
	public String insertComment(BookComment bc) {
//		int result = bookService.insertComment(bc);
		return "redirect:/book/detail?bookNo="+bc.getBookNo();
	}

	@GetMapping(value="list")
	public String list(Model model, int reqPage, int type, int genre) {
		BookListData bld = bookService.selectBookList(reqPage, type, genre);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		
		return "book/list";
	}
	
	
}




