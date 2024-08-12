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

import java.util.ArrayList;
import java.util.Arrays;
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
import kr.co.bookItOut.pay.model.dto.Pay;
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
	@GetMapping("/selectPayNames")
    @ResponseBody
    public List selectPayNames(@RequestParam("payNo") int payNo) {
        System.out.println("테스트");
        System.out.println("받은 payNo 값: " + payNo);
        
        // payNo에 해당하는 상품명을 조회
        List list = cartService.selectPayNames(payNo);
        List bookName = new ArrayList<String>();
        
        for(int i=0; i<list.size(); i++) {
        	bookName.add(((Pay)(list.get(i))).getBookName());
        }
        
        System.out.println(bookName);
        return bookName;  // JSON 형태로 반환됨
    }
	
	@ResponseBody
	@GetMapping(value="/addCart")
	public int addCart(int bookNo, @SessionAttribute Member member) {
		//System.out.println(bookNo);
		int memberNo = member.getMemberNo();		
		//System.out.println(memberNo);
		int result = cartService.insertCart(bookNo,memberNo);			
		return result;
	}
	
	
	@GetMapping(value="/nowPay")//
	public String nowPay (int bookNo, @SessionAttribute Member member, Model model) {
		
		System.out.println("나우페이");
		int memberNo = member.getMemberNo();
		
		List<Cart> list = cartService.insertCartNo(bookNo,memberNo);
		//카트 객체가 담긴 리스트
		
		
		String totalPrice = ((Cart)(list.get(0))).getBookPrice()+3000+"";
		//((Cart)(list.get(0))) >> list에 listdml 0번째 객체를 Cart 객체로 형변혼 >> 지금 상태는 Cart 
		//+""를 더한 이유는 selPay에서 totalPrice를 String으로 받고 있음
		
		List cartNo = new ArrayList<Integer>();
		cartNo.add(((Cart)(list.get(0))).getCartNo());//((Cart)(list.get(0)) >>Cart
		
		//selPay.html로 보내는 데이터
		model.addAttribute("list",list);
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("member", member);
		model.addAttribute("cartNo", cartNo); //모델로 html에 데이터 전송(총 4개)
		return "/cart/selPay";
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
	public String selDel(String no, @SessionAttribute(required=false) Member member, Model model) {
		
		int memberNo = member.getMemberNo();
		boolean result = cartService.selDel(no, memberNo);
		System.out.println(no);
		
		if(result) {
			System.out.println("삭제 성공");
			return "redirect:/cart/main";
		}else {
			System.out.println("삭제 실패");
			return "redirect:/cart/main";
		}
		
	}
	
	@PostMapping("/success")
	public String success(String cartNoStr, int price, @SessionAttribute(required=false) Member member, String name, String addr) {
		System.out.println("카트 넘버는 : "+cartNoStr);
		System.out.println(addr);
		System.out.println(name);
		boolean preResult = cartService.success1(price, member, addr, name);
		
		
		boolean result = cartService.success(cartNoStr, price, member);
		return "redirect:/cart/main";
	}
	
	
	@GetMapping("/selPay")
	public String selPay(String no, String bookCount, String totalPrice, Model model, @SessionAttribute(required=false) Member member) {
		System.out.println("책 넘버는 : "+no);
		System.out.println("수량은 : "+bookCount);
		//장바구니 수량 변경 시 결제화면에 반영 안됨
		
		
		int memberNo = member.getMemberNo();
		List list = cartService.selPay(memberNo, no, bookCount);
		
//		System.out.println("선택한 책 이름 /로 구분 -- "+name);
//		System.out.println("총 가격 -- "+totalPrice);
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("list", list);
		System.out.println(list);
		
		model.addAttribute("member",member);
		
		List cartNo = new ArrayList<Integer>();
		
		//System.out.println("카트넘버가져오기"+((Cart)list.get(0)).getCartNo());
		
		for(int i=0; i<list.size(); i++) {
			cartNo.add(((Cart)list.get(i)).getCartNo());
		}
		
		model.addAttribute("cartNo",cartNo);
		
		return "cart/selPay";
	}
	
	/*
 	@ResponseBody
	@GetMapping(value="/nowPay")
	public int nowPay(int bookNo, @SessionAttribute Member member) {
		int memberNo = member.getMemberNo();
		int result = cartService.insertCartNo(bookNo, memberNo);
		return result;
	}
 */

	
	
}
