package kr.co.bookItOut.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.bookItOut.cart.model.service.CartService;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.service.MemberService;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	
	@GetMapping(value="/main")
	public String loginFrm() {
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
	
}
