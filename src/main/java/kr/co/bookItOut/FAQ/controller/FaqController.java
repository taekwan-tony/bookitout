package kr.co.bookItOut.FAQ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.FAQ.model.service.FaqService;

@Controller
@RequestMapping(value="/FAQ")
public class FaqController {
	@Autowired
	private FaqService faqService;
	
	@GetMapping(value="/faqfrm")
	public String faqFrm() {
		return "FAQ/faqfrm";
	}
}
