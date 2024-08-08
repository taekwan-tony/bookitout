package kr.co.bookItOut.cart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

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

import jakarta.servlet.http.HttpSession;
import kr.co.bookItOut.cart.model.dto.Cart;
import kr.co.bookItOut.cart.model.service.CartService;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.service.MemberService;


import kr.co.bookItOut.util.FileUtils;


@Controller
@RequestMapping(value = "/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@Value("${file.root}")
	private String root;

	@Autowired
	private FileUtils fileUtils;// 파일업로드 처리 객체
	
	@Autowired
	private MemberService memberService;

	@GetMapping(value = "/main")
	public String loginFrm(Model model, @SessionAttribute(required=false) Member member) {
		int memberNo = member.getMemberNo();
		List list = cartService.selectAllCart(memberNo);
		model.addAttribute("list", list);
		
		return "cart/main";
	}
	

	
	@ResponseBody
	@GetMapping(value="/addCart")
	public int addCart(int bookNo, @SessionAttribute Member member, Model model) {
		System.out.println(bookNo);
		int memberNo = member.getMemberNo();		
		System.out.println(memberNo);
		int result = cartService.insertCart(bookNo,memberNo);	
		
		return result;
	}
	
	@ResponseBody
	@GetMapping(value="/selectCart")
	public int selectCart (int bookNo, @SessionAttribute Member member) {
		int memberNo = member.getMemberNo();
		int result = cartService.selectCart(bookNo, memberNo);
		return result;
	}
	
	

	@GetMapping("/selDel")
	public String selDel(String name, @SessionAttribute(required=false) Member member, Model model) {
		
		int memberNo = member.getMemberNo();
		boolean result = cartService.selDel(name, memberNo);
		System.out.println(name);
		
		if(result) {
			System.out.println("삭제 성공");
			return "redirect:/cart/main";
		}else {
			System.out.println("삭제 실패");
			return "redirect:/cart/main";
		}
		
	}
	
	@GetMapping("/selPay")
	public String selPay(String name, String totalPrice, Model model, @SessionAttribute(required=false) Member member) {
		int memberNo = member.getMemberNo();
		List list = cartService.selPay(memberNo, name);
		System.out.println(totalPrice);
		System.out.println(name);
		
		
		return "cart/selPay";
	}

}
