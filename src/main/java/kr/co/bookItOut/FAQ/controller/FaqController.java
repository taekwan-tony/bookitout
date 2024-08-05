package kr.co.bookItOut.FAQ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.FAQ.model.dto.Faq;
import kr.co.bookItOut.FAQ.model.dto.FaqListData;
import kr.co.bookItOut.FAQ.model.service.FaqService;

@Controller
@RequestMapping(value="/FAQ")
public class FaqController {
	@Autowired
	private FaqService faqService;
	
	@GetMapping(value="/faqfrm")
	public String faqFrm(int type,int reqPage,Model model) {
		FaqListData fld = faqService.selectAllFaq(type,reqPage);
		model.addAttribute("list",fld.getList());
		model.addAttribute("navi",fld.getPageNavi());
		model.addAttribute("type",fld.getFaqType());
		return "FAQ/faqfrm";
	}
	
	@GetMapping(value="/writeFaqFrm")
	public String writeFaqFrm() {
		return "FAQ/writeFaqFrm";
	}
	
	@PostMapping(value="/writeFaq")
	public String writeFaq(Faq f) {
		int result = faqService.writeFaq(f);
		if(result>0) {
			return "redirect:/FAQ/faqfrm?type=1&reqPage=1";
		}else {
			return "redirect:/FAQ/faqfrm?type=1&reqPage=1";			
		}
	}
}
