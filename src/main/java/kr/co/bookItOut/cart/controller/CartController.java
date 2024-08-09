package kr.co.bookItOut.cart.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/paySuccess")
	public String paySuccess() {
		
		return "cart/paySuccess";
	}
	
	@GetMapping("/success")
	public String success(@RequestParam("list") String listJson) {
		try {
            // JSON 문자열을 Java List로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> list = objectMapper.readValue(listJson, new TypeReference<List<String>>() {});
            
            // 리스트를 사용하여 필요한 작업 수행
            System.out.println(list);
            
            // 반환할 view나 다음 페이지로 이동
            return "successPage";
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 처리
            return "errorPage";
        }
	}
	
	
	@ResponseBody
	@GetMapping(value="/addCart")
	public int addCart(int bookNo, @SessionAttribute Member member) {
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

	
	@ResponseBody
	@GetMapping(value="/plusCart")
	public int plusCart (int cartNo) {
		int result = cartService.plusCart(cartNo);				
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
		
		//장바구니 수량 변경 시 결제화면에 반영 안됨
		int memberNo = member.getMemberNo();
		List list = cartService.selPay(memberNo, name);
		
//		System.out.println("선택한 책 이름 /로 구분 -- "+name);
//		System.out.println("총 가격 -- "+totalPrice);
		
		
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("list", list);
		
		//String memberName = member.getMemberName();
		model.addAttribute("member",member);
		
//		System.out.println("list : "+list);
		
		
		return "cart/selPay";
	}

}
