package kr.co.bookItOut.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.admin.model.dto.Admin;
import kr.co.bookItOut.admin.model.dto.AdminListData;
import kr.co.bookItOut.admin.model.service.AdminService;
import kr.co.bookItOut.member.model.dto.Member;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@GetMapping(value="/index")
	public String index(Model model,int rePage) {
		AdminListData ald = adminService.selectAdminList(rePage);
		
		return "adminIndex";
	}
	
	@GetMapping(value="/login")
	public String login(String memberId, String memberPw) {
		System.out.println(memberId);
		System.out.println(memberPw);
		Admin admin = adminService.selectOneMember(memberId,memberPw);
		System.out.println(admin);
		if(admin == null) {
			
		}
		return "redirect:/";
	}
	
	
	
	
	
}
