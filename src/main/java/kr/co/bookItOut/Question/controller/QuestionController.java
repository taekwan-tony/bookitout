package kr.co.bookItOut.Question.controller;

import java.util.ArrayList;
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

import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionFile;
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
	public String insertQuestion(Question q,MultipartFile[] upfile ,Model model) {
		String savepath = root+"/question";
		List<QuestionFile> fileList = new ArrayList<QuestionFile>();
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				QuestionFile qf = new QuestionFile();
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				qf.setFilename(filename);
				qf.setFilepath(filepath);
				fileList.add(qf);
			}
		}
		int result = questionService.insertQuestion(q,fileList); 
		
		return "redirect:/FAQ/faqfrm?type=1&reqPage=1";
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage" ,produces = "plain/text;charset=utf-8")
	public String editorImg(MultipartFile upfile) {
		String savepath = root+"/upload/editor";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/upload/editor/"+filepath;
	}
	
}
