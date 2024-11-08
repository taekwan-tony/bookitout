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
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookComment;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.book.model.dto.CenterMap;
import kr.co.bookItOut.book.model.service.BookService;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryBook;
import kr.co.bookItOut.util.FileUtils;
import kr.co.bookItOut.member.model.dto.Member;

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
	public String detail(Book book1, Model model, int bookNo, String check, @SessionAttribute(required = false) Member member) {
		Book book = bookService.selectOneBook(book1);
		model.addAttribute("book", book);
		int commentCount= bookService.selectBookCommentCount(bookNo);
				
		int memberNo = 0;
		if(member != null) {
			memberNo = member.getMemberNo();
		}
		Book b = bookService.selectOneBook(bookNo, check, memberNo);
		if(b == null) {
			model.addAttribute("title", "조회실패");
			model.addAttribute("msg", "해당 페이지가 존재하지 않습니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/book/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("b", b);
			model.addAttribute("commentCount", commentCount);
			//System.out.println(b);
			return "book/detail";
		}
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

		model.addAttribute("loc", "/book/detail?check=1&bookNo="+bc.getBookRef());
		return "common/msg";

		//return "redirect:/book/detail?bookNo="+bc.getBookRef();
	}
	
	@PostMapping(value="/updateComment")
	public String updateComment(BookComment bc, Model model) {
		int result = bookService.updateComment(bc);
		if(result > 0) {
			model.addAttribute("title", "성공");
			model.addAttribute("msg", "리뷰가 수정되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "잠시 후 다시 시도해주세요.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc", "/book/detail?check=1&bookNo="+bc.getBookRef());
		return "common/msg";
	}
	
	@GetMapping(value="/deleteComment")
	public String deleteComment(BookComment bc, Model model) {
		int result = bookService.deleteComment(bc);
		if(result > 0) {
			model.addAttribute("title", "성공");
			model.addAttribute("msg", "리뷰가 삭제되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "잠시 후 다시 시도해주세요.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc", "/book/detail?check=1&bookNo="+bc.getBookRef());
		return "common/msg";
	}
	
	@ResponseBody
	@PostMapping(value="/likePush")
	public int likePush(int bookCommentNo, int isLike, @SessionAttribute(required = false) Member member) {
		if(member == null) {
			return -10;
		}else {
			int memberNo = member.getMemberNo();
			int result = bookService.likePush(bookCommentNo, isLike, memberNo);
			return result;
		}
	}
	
	@ResponseBody
	@PostMapping(value="/centerMap") 
	public List<CenterMap> centerMap(int adminNo){
		List<CenterMap> centerMap = bookService.selectOneMap(adminNo);
		return centerMap;
	}
	
	@GetMapping(value="/centerMap")
	public String centerMap(int adminNo, String adminName, String adminAddr, String adminPhone, String openingDay, String latitude, String longitude, Model model) {
		//List<CenterMap> centerMap = bookService.selectOneMap(adminNo);
		model.addAttribute("adminNo", adminNo);
		model.addAttribute("adminName", adminName);
		model.addAttribute("adminAddr", adminAddr);
		model.addAttribute("adminPhone", adminPhone);
		model.addAttribute("openingDay", openingDay);
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);
		return "book/centerMap";
	}

	@GetMapping(value="/list")
	public String list(Model model, int reqPage, int type, int genre, int group) {
		BookListData bld = bookService.selectBookList(reqPage, type, genre, group);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		model.addAttribute("genre",genre);
		model.addAttribute("group",group);
		return "book/list";
	}
	
	@GetMapping(value="listFore")
	public String listFore(Model model, int reqPage, int type, int genre, int group) {
		BookListData bld = bookService.selectBookListFore(reqPage, type, genre, group);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		model.addAttribute("genre", genre);	
		model.addAttribute("group",group);
		return "book/listFore";
	}
	
}