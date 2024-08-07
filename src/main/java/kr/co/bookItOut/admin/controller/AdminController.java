package kr.co.bookItOut.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.admin.model.service.AdminService;
import kr.co.bookItOut.book.model.dto.BookListData;
import kr.co.bookItOut.member.model.dto.Member;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
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
		System.out.println(memberId);
		System.out.println(memberPw);
		Admin admin = adminService.selectOneMember(memberId,memberPw);
		System.out.println(admin);
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
		return "/admin/insertAdmin";
	}
	//판매점 비밀번호 찾기
	@GetMapping(value = "/updatePwFrm")
	public String updatePwFrm() {
		return "/admin/updatePw";
	}
//책 부분 시작------------------------------------------------------------------------------------------------
//책 리스트 보여주는 화면
	@GetMapping(value = "/bookListFrm")
	public String bookListFrm(Model model,int rePage) {
		BookListData bld = adminService.selectbookList(rePage);
		model.addAttribute("bookList",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		
		return "/admin/bookList";
	}
	
	@PostMapping(value = "/bookList")
	public String bookList() {
		
		return "/admin/bookList";
	}
	//--
	@GetMapping(value = "/insertBookFrm")
	public String insertBook() {
		
		return "/admin/insertBook";
	}

	
	
	
}
