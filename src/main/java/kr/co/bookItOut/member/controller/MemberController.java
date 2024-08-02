package kr.co.bookItOut.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.member.model.service.MemberService;

@Controller
@RequestMapping(value="/member")
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
}
