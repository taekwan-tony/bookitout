package kr.co.bookItOut.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping(value = "/mypage")
	private String myPage() {
		return "myPage/myPage";
	}

	@GetMapping(value = "/myInfo")
	private String myInfo() {
		return "myPage/myInfo";
	}

	@GetMapping(value = "/myOrder")
	private String myOrder() {
		return "myPage/myOrder";
	}

	
	@GetMapping(value="/loginFrm")
	public String loginFrm() {
		return "member/login";
	}
	
	@PostMapping(value="/login")
	public String login(String memberId, String memberPw, int role, HttpSession session) {
		
		System.out.println(role);
		
		
		if(role==1) {
		Member member = memberService.selectOneMember(memberId,memberPw);
		System.out.println(member);
		session.setAttribute("member", member);
		
		return "redirect:/";
		}else{
			return "redirect:/admin/login?memberId="+memberId+"&memberPw="+memberPw;
		}
	}
	
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
//	@PostMapping(value = "/login")
//	public String login(Member m, Model model, HttpSession session) {
//		// service를 이용해서 DB에 입력받은 아이디/패스워드가 일치하는 회원 조회
//		// 조회조건에 아이디가 포함되어있으므로 조회결과는 회원1명 또는 0명 -> Member타입으로 결과 받기
//		Member member = memberService.selectOneMember(m);
//		if (member == null) {
//			
//			return "member/logIn";
//		} else {
//			session.setAttribute("member", member);
//			return "redirect:/";
//		}
//	}
}
