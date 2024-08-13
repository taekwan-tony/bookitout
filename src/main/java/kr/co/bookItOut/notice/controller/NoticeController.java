package kr.co.bookItOut.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bookItOut.FAQ.model.dto.FaqListData;
import kr.co.bookItOut.notice.dto.Notice;
import kr.co.bookItOut.notice.dto.NoticeListData;
import kr.co.bookItOut.notice.service.NoticeService;
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/noticeFrm")
	public String noticeFrm(int reqPage,Model model) {
		NoticeListData nld = noticeService.selectAllNotice(reqPage);
		model.addAttribute("list",nld.getList());
		model.addAttribute("pageNavi",nld.getPageNavi());
		model.addAttribute("totalCount",nld.getTotalCount());
		return "notice/noticeList";
	}
	
	@GetMapping(value="/noticeDetail")
	public String noticeDetail(int noticeNo,Model model) {
		Notice notice = noticeService.selectOneNotice(noticeNo);
		model.addAttribute("notice",notice);
		model.addAttribute("noticeData",notice.getNoticeData());
		return "notice/noticeDetail";
		
	}
	@GetMapping(value="/writeNoticeFrm")
	public String writeNoticeFrm() {
		return "notice/noticeWriteFrm";
	}
	
	
	@PostMapping(value="/writeNotice")
	public String wirteNotice(Notice n,Model model) {
		int result = noticeService.insertNotice(n);
		if(result>0) {
			model.addAttribute("title","공지사항 작성 완료");
			model.addAttribute("msg","공지사항 작성 완료했습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","공지사항 작성 실패");
			model.addAttribute("msg","오류로 인하여 작성에 실패했습니다. 관리자에게 문의하세요");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/notice/noticeFrm?reqPage=1");
		return "common/msg";
	}
	
	@GetMapping(value="/updateFrm")
	public String updateFrm(int noticeNo,Model model) {
		Notice n = noticeService.selectOneNotice(noticeNo);
		model.addAttribute("notice",n);
		return "notice/updateFrm";
	}
	
	@PostMapping(value="/updateNotice")
	public String updateNotice(Notice n,Model model) {
		int result = noticeService.updateNotice(n);
		if(result>0) {
			model.addAttribute("title","공지사항 작성 완료");
			model.addAttribute("msg","공지사항 작성 완료했습니다.");
			model.addAttribute("icon","success");
		}else {
			model.addAttribute("title","공지사항 작성 실패");
			model.addAttribute("msg","오류로 인하여 작성에 실패했습니다. 관리자에게 문의하세요");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/notice/noticeDetail?noticeNo="+n.getNoticeNo());
		return "common/msg";
	}
	
	@GetMapping(value="/deleteNotice")
	public String deleteNotice(int noticeNo,Model model) {
		int result = noticeService.deleteNotice(noticeNo);
		if(result>0) {
			model.addAttribute("title","삭제 완료");
			model.addAttribute("msg","공지사항 삭제 완료했습니다.");
			model.addAttribute("icon","success");
			
		}else {
			model.addAttribute("title","삭제 실패");
			model.addAttribute("msg","오류로 인하여 작성에 실패했습니다. 관리자에게 문의하세요");
			model.addAttribute("icon","error");
		}
		model.addAttribute("loc","/notice/noticeFrm?reqPage=1");
		return "common/msg";
		
	}
	
	@GetMapping(value="/searchNotice")
	public String searchFaq(int reqPage,String keyword,Model model) {
		NoticeListData nld = noticeService.searchNotice(reqPage,keyword);
		model.addAttribute("list",nld.getList());
		model.addAttribute("pageNavi",nld.getPageNavi());
		model.addAttribute("keyword",keyword);
		model.addAttribute("totalCount",nld.getTotalCount());
		return "notice/noticeList";
	}
}
