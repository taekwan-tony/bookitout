package kr.co.bookItOut.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.pay.model.service.PayService;

@Controller
@RequestMapping(value = "/pay")
public class PayController {

	@Autowired
	private PayService payService;
	
	@GetMapping("/paySuccess")
	public String paySuccess() {
		
		return "cart/paySuccess";
	}
	
}
