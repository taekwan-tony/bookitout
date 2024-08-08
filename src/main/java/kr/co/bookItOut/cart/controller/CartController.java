package kr.co.bookItOut.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bookItOut.cart.model.dto.Cart;
import kr.co.bookItOut.cart.model.service.CartService;
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

	@GetMapping(value = "/main")
	public String loginFrm(Model model) {

		List list = cartService.selectAllCart();

		
		model.addAttribute("list", list);
		return "cart/main";
	}
	
	@GetMapping("/selDel")
	public String selDel(String name) {
		System.out.println(name);
		
		return "redirect:/cart/main";
	}
}
