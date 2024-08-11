package kr.co.bookItOut.FAQ.controller;

import java.io.File;
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
import kr.co.bookItOut.Question.model.dto.QuestionFile;
import kr.co.bookItOut.Question.model.service.QuestionService;
import kr.co.bookItOut.notice.service.NoticeService;

@Controller
@RequestMapping(value="/FAQ")
public class FaqController {
	@Autowired
	private FaqService faqService;
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping(value="/customerMain")
	public String customerMain(Model model) {
		List list = noticeService.selectAllNotice();
		model.addAttribute("list",list);
		return "FAQ/customerMain";
	}
	
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
	
	@GetMapping(value="/searchFaq")
	public String searchFaq(int type,int reqPage,String searchQna,Model model) {
		FaqListData fld = faqService.searchFaq(type,reqPage,searchQna);
		model.addAttribute("list",fld.getList());
		model.addAttribute("navi",fld.getPageNavi());
		model.addAttribute("type",fld.getFaqType());
		model.addAttribute("searchQna",searchQna);
		return "FAQ/faqfrm";
	}
	
	
	@GetMapping(value="/deleteFaq")
	public String deleteQuestion(int faqNo,Model model) {
		int result = faqService.deleteFaq(faqNo);
		if(result>0) {
			model.addAttribute("title","삭제 성공");
			model.addAttribute("msg","삭제 성공!");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/FAQ/faqfrm?type=1&reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title","삭제 실패");
			model.addAttribute("msg","처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/FAQ/faqfrm?type=1&reqPage=1");
			return "common/msg";
		}
	}
	
	@GetMapping(value="/updateFaqFrm")
	public String updateFaqFrm(int faqNo,Model model) {
		Faq f = faqService.selectOneFaq(faqNo);
		model.addAttribute("faq",f);
		return "FAQ/updateFrm";
	}
	
	@PostMapping(value="/updateFaq")
	public String updateFaq(Faq f,Model model) {
		int result = faqService.updateFaq(f);
		if(result>0) {
			model.addAttribute("title","수정 성공");
			model.addAttribute("msg","수정 성공!");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/FAQ/faqfrm?type=1&reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title","수정 실패");
			model.addAttribute("msg","처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/FAQ/faqfrm?type=1&reqPage=1");
			return "common/msg";
		}
		
	}
}
