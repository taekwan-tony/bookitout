package kr.co.bookItOut.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionListData;
import kr.co.bookItOut.Question.model.service.QuestionService;
import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminCenterBook;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.admin.model.dto.AdminOrderBook;
import kr.co.bookItOut.admin.model.dto.OrderBookListData;
import kr.co.bookItOut.admin.model.dto.OrderListData;
import kr.co.bookItOut.admin.model.service.AdminService;
import kr.co.bookItOut.book.model.dto.AdminBook;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.book.model.service.BookService;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventory;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.util.EmailSender;

import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	/**
	 * 1:1문의 service 어노테이션
	 * **/
	@Autowired
	private QuestionService questionService;
	@Autowired
	private EmailSender emailSender;
	
	@Value("${file.root}")
	private String root;//application.properties에 설정되어있는 file.root값을 가지고 와서 문자열로 저장
	
	@Autowired
	private FileUtils fileUtils; //파일업로드 처리해줄 객체

	
	//판매점 리스트
	@GetMapping(value="/adminIndex")
	public String index(Model model,int rePage,@SessionAttribute(required = false) Admin admin) {
		AdminListData ald = adminService.selectAdminList(rePage,admin);
		model.addAttribute("list",ald.getList());
		model.addAttribute("pageNavi",ald.getPageNavi());
		
		return "adminIndex";
	}
	//로그인
	@GetMapping(value="/login")
	public String login(String memberId, String memberPw, HttpSession session,Model model) {
		Admin admin = adminService.selectOneMember(memberId,memberPw);
		session.setAttribute("admin", admin);
		return "redirect:/admin/adminIndex?rePage=1";
	}
	
	
	//로그아웃
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	
	//판매점 등록
	@GetMapping(value = "/insertAdminFrm")
	public String insertAdminFrm() {
		return "admin/insertAdmin";
	}
	
	
	//판매점 비밀번호 찾기
	@GetMapping(value = "/updatePwFrm")
	public String updatePwFrm() {
		return "admin/updatePw";
	}
//책 부분 시작------------------------------------------------------------------------------------------------
//책 리스트 보여주는 화면
	//판매점 && 총관리자 전부다 볼수있는 책리스트
	@GetMapping(value = "/bookListFrm")
	public String bookListFrm(Model model,int rePage) {
		BookListData bld = adminService.selectbookList(rePage);
		
		model.addAttribute("bookList",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		
		return "admin/bookList";
	}
	
	//판매점 내 책 리스트(가맹점 안에있는 책)
	@GetMapping(value = "/admin2BookList")
	public String admin2BookList(Model model,int reqPage,Book book,@SessionAttribute(required=false) Admin admin) {
		
		OrderBookListData obld = adminService.selectAdminBookList(reqPage,book,admin);
					
		model.addAttribute("Admin2BookList",obld.getList());
		model.addAttribute("pageNavi",obld.getPageNavi());
		
		return "admin/admin2BookList";
	}
	
	
	
	@GetMapping(value = "/insertBookFrm")
	public String insertBook() {
		
		
		return "admin/insertBook";
	}
	//등록된 책 보내주기
	@PostMapping(value = "/insertBook")
	public String insertBook(Book book,MultipartFile upfileImg,MultipartFile upfileDetailImg,Model model ,@SessionAttribute(required = false) Admin admin) {
		
		String savepath = root+"/book/";
		
		String filePath = fileUtils.upload(savepath,upfileImg);
		
		String filePath1 = fileUtils.upload(savepath, upfileDetailImg);
		book.setAdminNo(admin.getAdminNo());
		book.setBookImg(filePath);
		book.setBookDetailImg(filePath1);
		int result = adminService.insertBook(book,admin);
		if(result>0) {
			model.addAttribute("title","등록성공");
			model.addAttribute("msg","책 등록에 성공했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/bookListFrm?rePage=1");
			return "common/msg";
		}
			
		return "redirect:/admin/bookListFrm?rePage=1";
		
	}
	//책 삭제
	@GetMapping(value = "/delete")
	public String delete(int bookNo, Model model) {
		Book book = adminService.deleteBook(bookNo);
		
		if(book == null) {	model.addAttribute("title","삭제실패");
		model.addAttribute("msg","존재하지 않는 게시물");
		model.addAttribute("icon","error");
		model.addAttribute("loc","/admin/bookListFrm?rePage=1");
		return "common/msg";
			
		}else {
			model.addAttribute("title","삭제 성공!");
			model.addAttribute("msg","게시글이 삭제되었습니다");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/bookListFrm?rePage=1");
		
		}
		return "common/msg";
		//return "admin/insertBook";
		
	}
	//책 수정 //장르 타입 책이름 저자 출판사 판매가격
	
	@GetMapping(value = "/updateBook",produces="plain/text;charset=utf-8")
	public String updateBook(Book book, Model model) {
		int result = adminService.updatebookList(book);
		
		
		if( result>0) {	model.addAttribute("title","수정성공");
		model.addAttribute("msg","책 수정에 성공했습니다");
		model.addAttribute("icon","success");
		model.addAttribute("loc","/admin/bookListFrm?rePage=1");
		return "common/msg";
		}else {
			model.addAttribute("title","수정실패!");
			model.addAttribute("msg","책 수정에 실패 했습니다");
			model.addAttribute("icon","error");	
			model.addAttribute("loc","/admin/bookListFrm?rePage=1");
		}
		return "common/msg";
	}
	//상세 수정 (시작-- book번호 주고 그 번호에 맞는 정보들 화면에 보여주기) 
	 @GetMapping("/updateDetailFrm")
	    public String updateDetailPage(int bookNo , Model model) {
		 Book book = adminService.selectOneBook(bookNo);
		 
		 book.getBookImg();
		 book.getBookDetailImg();
		 
		 model.addAttribute("book",book);
		 
		 
	        return "admin/updateDetail"; 
	    }
	 @PostMapping(value = "/updateDetail")
		public String updateDetail(Book b,MultipartFile upfileImg,MultipartFile upfileDetailImg,Model model) {
		 
		 String savepath = root+"/notice/";
		 String filePath = fileUtils.upload(savepath,upfileImg);
		 String filePath1 = fileUtils.upload(savepath, upfileDetailImg);
		 b.setBookNo(b.getBookNo());
		 b.setBookImg(filePath);
		 b.setBookDetailImg(filePath1);
		 
		 int result = adminService.updateDetailBook(b);
		 
		 if( result>0) {	model.addAttribute("title","수정성공");
			model.addAttribute("msg","책 수정에 성공했습니다");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/bookListFrm?rePage=1");
			return "common/msg";
			}else {
				model.addAttribute("title","수정실패!");
				model.addAttribute("msg","책 수정에 실패 했습니다");
				model.addAttribute("icon","error");	
				model.addAttribute("loc","/admin/bookListFrm?rePage=1");
			}
			return "common/msg";
		}
	 //발주 버튼을 눌렀을때 
	 //select
	 @GetMapping(value = "/orderAdmin2")
	 public String orderAdmin2(int bookNo,@SessionAttribute(required=false) Admin admin,Model model) {
		 
		 AdminCenterBook acb = adminService.selectOneOrder(bookNo,admin);
		 
		 model.addAttribute("order",acb);
		 
		 return "admin/orderAdmin2"; 
	 }
	 
	 
	 //--발주창-------------------------------------------------
	 	//판매자가 값을 총관리자에게 보내주기
	 	@GetMapping(value = "/centerInventoryOrder")
	 	public String centerInventoryOrder(int centerBookNo, int orderBookCount ,Model model,@SessionAttribute(required=false) Admin admin) {
	 		System.out.println(centerBookNo);
	 		int result = adminService.insertOrderAdmin(centerBookNo,orderBookCount,admin);
	 		if( result>0) {model.addAttribute("title","완료");
			model.addAttribute("msg","발주완료");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/orderList?type=1&reqPage=1");
			return "common/msg";
			}else {
				model.addAttribute("title","실패!");
				model.addAttribute("msg","실패 했습니다");
				model.addAttribute("icon","error");	
				model.addAttribute("loc","/admin/orderList?type=1&reqPage=1");
			}
			return "common/msg";
	 		
	 	//	return "redirect:/admin/bookListFrm?rePage=1";
	 	}
	 	//orderList
	 	//판매자가 보는 화면 
	 	@GetMapping(value = "/orderList")
	 	public String orderList(@SessionAttribute(required =false) Admin admin, int type ,int reqPage,Model model ) {
	 		System.out.println(admin);
	 		String click = null;
	 		switch(type) {
	 		case 1 : 
	 			click="발주중";
	 			break;
	 		case 2 : 
	 			click="반려";
	 			break;
	 		case 3 : 
	 			click="승인";
	 			break;
	 		}
	 		OrderListData old = adminService.selectAllOrder(admin,type,reqPage);
	 		model.addAttribute("list",old.getList());
	 		model.addAttribute("pageNavi",old.getPageNavi());
	 		model.addAttribute("click",click);
	 		return "admin/orderList";
	 	}
	 	
	 	
	 	//총관리자 select 부분(발주)
	 	//총관리자 발주된 책 리스트
	@GetMapping(value = "/order")
 		  public String orderAdmin1(Model model,int reqPage) {
			OrderListData old =  adminService.selectAllOrderList(reqPage);
			
			model.addAttribute("orderList",old.getList());
			model.addAttribute("pageNavi",old.getPageNavi());
			
			
			return "admin/order";
		}
	// 발주 변경
	@GetMapping(value = "/orderCheck")
	public String orderCheck(int orderAllCheck,int orderNo, Model model,int orderQuntity ,int bookNo,int adminNo) {
		int result = adminService.updateOrdercheck(orderAllCheck,orderNo,orderQuntity,bookNo,adminNo);
		 if( result>0) {	model.addAttribute("title","발주성공");
			model.addAttribute("msg","발주 성공했습니다");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/order?reqPage=1");
			return "common/msg";
			}else {
				model.addAttribute("title","수정실패!");
				model.addAttribute("msg"," 발주에 실패 했습니다");
				model.addAttribute("icon","error");	
				model.addAttribute("loc","/admin/order?reqPage=1");
			}
			return "common/msg";
	}
	 	
	 	
	 	//update-> 발주 승인하면 
	 
	 
	 
//------------------------------------------------------------------------------------------------------------------------	 
	 
	 
	 
	 
//1:1문의 관리자 처리 
	@GetMapping(value="/questionAnswer")
	public String questionAnswerList(int reqPage,Model model) {
		QuestionListData qld = questionService.selectAllQuestion(reqPage);
		model.addAttribute("list",qld.getList());
		model.addAttribute("pageNavi",qld.getPageNavi());
		return "admin/questionAnswer";
	}
	
	@GetMapping(value="/questionAnswerFrm")
	public String questionAnswerFrm(int questionNo,Model model) {
		Question q = questionService.selectOneQuestion(questionNo);
		List fileList = questionService.selectAllFile(questionNo);
		q.setFileList(fileList);
		model.addAttribute("q",q);
		return "admin/questionAnswerFrm";
	}
	
	@PostMapping(value="/questionAnswerCompelte")
	public String questionAnswerCompelte(Question q,Model model) {
		String emailTitle = "책키라웃 1:1문의 답변 처리 완료 메일 송부의 건";
		String receiver = q.getQuestionEmail();
		String emailContent = "1:1문의 답변이 완료되었습니다.";
		
		emailSender.sendMail(emailTitle, receiver, emailContent);
		
		int result = questionService.updateQuestionAnswer(q);
		
		if(result>0) {
			if(result>0) {
				model.addAttribute("title","답변완료");
				model.addAttribute("msg","문의를 처리했습니다.");
				model.addAttribute("icon","success");
				model.addAttribute("loc","/admin/questionAnswer?reqPage=1");
				return "common/msg";
			}
		}
		return "/admin/questionAnswer?reqPage=1";
		
	}
	
	
	
	
	
}
