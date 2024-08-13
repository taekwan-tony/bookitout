package kr.co.bookItOut.member.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.book.model.dto.BookComment;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.service.MemberService;
import kr.co.bookItOut.util.EmailSender;
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	@Autowired
	private EmailSender emailSender;

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
	private String myOrder(@SessionAttribute(required=false) Member member, Model model) {
		int memberNo = member.getMemberNo();
		List list = memberService.selectAllPay(memberNo);
		
		model.addAttribute("list", list);
		
		return "myPage/myOrder";
	}
	
	@GetMapping(value = "/myBoard")
	private String myBoard(@SessionAttribute(required=false) Member member, Model model) {
		String memberId = member.getMemberId();
		List list = memberService.selectMyBoard(memberId);
		
		model.addAttribute("list", list);
		System.out.println(list);
		return "myPage/myBoard";
	}
	
	@GetMapping(value = "/myReview")
	private String myReview(@SessionAttribute(required=false) Member member, Model model) {
		String memberId = member.getMemberId();
		List<BookComment> list = memberService.selectCommentList(memberId);
		
		model.addAttribute("list", list);
		model.addAttribute("member", member);
		System.out.println(list);
		return "myPage/myReview";
	}

	@GetMapping(value = "/loginFrm")
	public String loginFrm() {
		return "member/login";
	}

	@PostMapping(value = "/login")
	public String login(String memberId, String memberPw, int role, HttpSession session, Model model) {
		System.out.println(role);

		if (role == 1) {
			Member member = memberService.selectOneMember(memberId, memberPw);
			System.out.println(member);
			session.setAttribute("member", member);
			if(member==null) {
				String error = "아이디 또는 비밀번호가 일치하지 않습니다.";
				model.addAttribute("error",error);
				return "member/login";
			}else {				
				return "redirect:/";
			}
		} else {
			return "redirect:/admin/login?memberId=" + memberId + "&memberPw=" + memberPw;
		}
	}

	@GetMapping(value = "/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@ResponseBody
	@GetMapping(value = "/ajaxCheckId")
	public int ajaxCheckId(String memberId) {
		Member member = memberService.selectOneMember(memberId);
		if (member == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@PostMapping(value = "/join")
	public String logout(Member m, Model model) {
		int result = memberService.insertMember(m);
		if (result > 0) {
			System.out.println("회원가입 성공");
			return "redirect:/";
		} else {
			System.out.println("회원가입 실패");
			return "redirect:/";
		}
	}

	@ResponseBody
	@PostMapping(value = "/sendCode")
	public String sendCode(String receiver) {
		String emailTitle = "책키라웃 인증메일입니다.";
		// 인증메일 인증코드 생성
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {

			int flag = r.nextInt(3); // 0,1,2 ->숫자,대문자,소문자 중 결정
			if (flag == 0) {
				int randomCode = r.nextInt(10);
				sb.append(randomCode);
			} else if (flag == 1) {
				char randomCode = (char) (r.nextInt(26) + 65);
				sb.append(randomCode);
			} else if (flag == 2) {
				char randomCode = (char) (r.nextInt(26) + 97);
				sb.append(randomCode);
			}
		}

		String emailContent = "<h1>안녕하세요. 책키라웃 입니다.</h1>"
							+ "<h3>인증번호는 [<span style='color:red;'>"
							+ sb.toString() 
							+ "</span>]입니다.</h3>";
		
		emailSender.sendMail(emailTitle, receiver, emailContent);
		return sb.toString();
	}
	
	@GetMapping(value = "/searchIdFrm")
	public String searchIdFrm() {
		
		return "member/searchIdFrm";
	}
	
	@GetMapping(value = "/searchPwFrm")
	public String searchPwFrm() {
		
		return "member/searchPwFrm";
	}
	
	@PostMapping(value = "/searchId")
	public String searchId(Member m, Model model) {
			Member member = memberService.selectSearchId(m);
			String memberId;
			
			if(member == null) {
				memberId = "아이디가 존재하지 않습니다.";
			}else {				
				memberId = member.getMemberId();
			}
			model.addAttribute("memberId", memberId);
			return "member/searchId";
	}
	
	@PostMapping(value = "/searchPw")
	public String searchPw(Member m, Model model) {
			Member member = memberService.selectSearchPw(m);
			String memberPw;
			
			if(member == null) {
				memberPw = "계정이 존재하지 않습니다.";
			}else {
				memberPw = member.getMemberPw();				
			}
			
			model.addAttribute("memberPw", memberPw);
			return "member/searchPw";
	}

	@PostMapping(value = "/updateInfo")
	public String updateInfo(Member m, HttpSession session, MultipartFile imageFile) {

		int sel;
		System.out.println(imageFile);
		// 이미지 파일 저장 경로 지정
		if (!imageFile.isEmpty()) {
			sel = 1;
			String savepath = root + "/photo/";
			String filepath = fileUtils.upload(savepath, imageFile);
			m.setMemberImg(filepath);
		} else {
			sel = 2;
		}

		// 사용자 정보 업데이트
		Member member = memberService.selectOneMember(m.getMemberId());
		int result = memberService.updateMember(member, m, sel);

		if (result > 0) {
			System.out.println("수정 성공");
			session.setAttribute("member", memberService.selectOneMember(m.getMemberId()));
			// 사용자의 세션 데이터를 업데이트하는 코드
			// session 객체는 현재 사용자의 세션, setAttribute 메서드는 세션에 속성을 저장하거나 업데이트할 때 사용
			// 매개변수 1 세션에 저장할 속성의 이름
			// 매개변수 2 세션에 저장할 실제 데이터

			return "/myPage/myInfo";
		} else {
			System.out.println("수정 실패");
			return "/myPage/myInfo";
		}
	}
	
	@GetMapping(value = "/details")
	public String details(int payNo) {
		
		return "member/searchPwFrm";
	}
	
	@ResponseBody
	@PostMapping(value = "/boardView")
	public String boardView(int boardNo) {
	System.out.println("boardNo"+boardNo);
	
	return "board/view";
	}
}
