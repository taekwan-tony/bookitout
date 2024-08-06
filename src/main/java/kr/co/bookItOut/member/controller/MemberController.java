package kr.co.bookItOut.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.service.MemberService;
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;

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
	
	@ResponseBody
	@GetMapping(value = "/ajaxCheckId")
	public int ajaxCheckId(String memberId) {
		Member member = memberService.selectOneMember(memberId);
		if(member == null) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@PostMapping(value="/join")
	public String logout(Member m, Model model) {
		int result = memberService.insertMember(m);
		if(result>0) {
			System.out.println("회원가입 성공");
			return "redirect:/";
		}else {
			System.out.println("회원가입 실패");
			return "redirect:/";
		}
	}
	
	
	@PostMapping(value="/updateInfo")
	public String updateInfo(Member m, HttpSession session, MultipartFile imageFile) {
		
		System.out.println(imageFile);
		//이미지 파일 저장 경로 지정
		if(imageFile !=null) {
			String savepath = root+"/photo/";
			String filepath = fileUtils.upload(savepath, imageFile);
			m.setMemberImg(filepath);			
		}
		
		//사용자 정보 업데이트
		Member member = memberService.selectOneMember(m.getMemberId());
		int result = memberService.updateMember(member, m);
		
		if(result>0) {
			System.out.println("수정 성공");
			session.setAttribute("member", memberService.selectOneMember(m.getMemberId()));
			//사용자의 세션 데이터를 업데이트하는 코드
			//session 객체는 현재 사용자의 세션, setAttribute 메서드는 세션에 속성을 저장하거나 업데이트할 때 사용
			//매개변수 1 세션에 저장할 속성의 이름
			//매개변수 2 세션에 저장할 실제 데이터
			
			return "redirect:/";
		}else {
			System.out.println("수정 실패");
			return "redirect:/";
		}
	}
}
