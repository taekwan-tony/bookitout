package kr.co.bookItOut.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionListData;
import kr.co.bookItOut.Question.model.service.QuestionService;
import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.admin.model.service.AdminService;
import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.book.model.dto.BookListData;

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
	//책 삭제
	@GetMapping(value = "/delete")
	public String delete(int bookNo, Model model) {
		int result = adminService.deleteBook(bookNo);
		if(result>0) {
			model.addAttribute("title","삭제 성공!");
			model.addAttribute("msg","게시글이 삭제되었습니다");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/admin/bookListFrm?reqPage=1");
		}else {
			model.addAttribute("title","삭제실패");
			model.addAttribute("msg","존재하지 않는 게시물");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/admin/bookListFrm?reqPage=1");
			return "common/msg";
		}
		return "common/msg";
		//return "admin/insertBook";
		
	}
	
	

	
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
