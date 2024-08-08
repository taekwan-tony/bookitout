package kr.co.bookItOut.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.admin.model.service.AdminService;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Value("${file.root}")
	private String root;//application.properties에 설정되어있는 file.root값을 가지고 와서 문자열로 저장
	
	@Autowired
	private FileUtils fileUtils; //파일업로드 처리해줄 객체
	
	//판매점 리스트
	@GetMapping(value="/adminIndex")
	public String index(Model model,int rePage) {
		AdminListData ald = adminService.selectAdminList(rePage);
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
	@GetMapping(value = "/bookListFrm")
	public String bookListFrm(Model model,int rePage) {
		BookListData bld = adminService.selectbookList(rePage);
		model.addAttribute("bookList",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		
		return "admin/bookList";
	}
	
//	@PostMapping(value = "/bookList")
//	public String bookList() {
//		
//		return "admin/bookList";
//	}
	//--책등록 화면
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
		int result = adminService.insertBook(book);
		if(result>0) {
			model.addAttribute("title","등록성공");
			model.addAttribute("msg","책 등록에 성공했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/bookListFrm?rePage=1");
			return "common/msg";
		}
			
		return "redirect:/admin/bookListFrm?rePage=1";
		
	}
	

	
	
	
	
	
}
