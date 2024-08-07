package kr.co.bookItOut.centerInventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.centerInventory.model.service.CenterInventoryService;

@Controller
@RequestMapping(value="/book")
public class CenterInventoryController {
	@Autowired
	private CenterInventoryService centerInventoryService;
	
	@GetMapping(value="/detail")
	public String detail(Model model) {
		List list = centerInventoryService.selectAllCenterInventory();
		model.addAttribute("list", list);
		return "book/detail";
	}
}
