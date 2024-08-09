package kr.co.bookItOut.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.notice.dto.NoticeListData;
import kr.co.bookItOut.notice.service.NoticeService;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping(value="/noticeFrm")
	public String noticeFrm(int reqPage,Model model) {
		NoticeListData nld = noticeService.selectAllNotice(reqPage);
		model.addAttribute("list",nld.getList());
		model.addAttribute("pageNavi",nld.getPageNavi());
		return "/notice/noticeList";
	}
	
}
