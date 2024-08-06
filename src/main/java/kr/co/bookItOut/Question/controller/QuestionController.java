package kr.co.bookItOut.Question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	
	@GetMapping(value="/writeQuestionFrm")
	public String writeQuestionForm() {
		return "question/onebyoneFrm";
	}
	
	@PostMapping(value="/insertQuestion")
	public String insertQuestion(Question q,Model model) {
		return null;
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage" ,produces = "plain/text;charset=utf-8")
	public String editorImg(MultipartFile upfile) {
		String savepath = root+"/upload/editor";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/upload/editor/"+filepath;
	}
	
}
