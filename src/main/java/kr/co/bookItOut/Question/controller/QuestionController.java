package kr.co.bookItOut.Question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.service.QuestionService;
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value="/Question")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private FileUtils fileUtils;
	@Value("${file.root}")
	private String root;
	
	@PostMapping(value="/insertQuestion")
	public String insertQuestion(Question q,Model model) {
		return null;
	}
	
}
