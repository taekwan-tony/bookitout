package kr.co.bookItOut.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.pay.model.service.PayService;

@Controller
@RequestMapping(value = "/pay")
public class PayController {

	@Autowired
	private PayService payService;
	
	
}
