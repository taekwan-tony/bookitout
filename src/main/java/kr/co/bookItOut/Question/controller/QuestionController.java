package kr.co.bookItOut.Question.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionFile;
import kr.co.bookItOut.Question.model.dto.QuestionListData;
import kr.co.bookItOut.Question.model.service.QuestionService;
import kr.co.bookItOut.member.model.dto.Member;
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
		String savepath = root+"/question/";
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
		
		return "redirect:/Question/questionList?type=1&reqPage=1";
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage" ,produces = "plain/text;charset=utf-8")
	public String editorImg(MultipartFile upfile) {
		String savepath = root+"/upload/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/upload/editor/"+filepath;
	}
	
	@GetMapping(value="/questionList")
	public String questionList(@SessionAttribute(required=false) Member member,int type,int reqPage,Model model) {
		int memberNo =41;
		if(member !=null) {
			memberNo = member.getMemberNo();
		}
		String click =null;
		switch(type) {
		case 1 :
			click = "전체";
			break;
		case 2 : 
			click = "처리중";
			break;
		case 3 :
			click = "완료";
			break;
		}
		QuestionListData qld = questionService.selectAllQuestion(memberNo,type,reqPage);
		model.addAttribute("list",qld.getList());
		model.addAttribute("navi",qld.getPageNavi());
		model.addAttribute("click",click);
		return "question/questionList";
	}
	
	@GetMapping(value="/updateQuestionFrm")
	public String updateQuestionFrm(int questionNo,Model model) {
		Question q = questionService.selectOneQuestion(questionNo);
		List fileList = questionService.selectAllFile(questionNo);
		q.setFileList(fileList);
		model.addAttribute("q",q);
		return "question/updateQuestionFrm";
		
	}
	
	@PostMapping(value="/updateQuestion")
	public String updateQuestion(Question q,MultipartFile[] upfile,int[] delFileNo,Model model) {
		List<QuestionFile> fileList = new ArrayList<QuestionFile>();
		String savepath = root+"/question/";
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				QuestionFile qf = new QuestionFile();
				qf.setFilename(filename);
				qf.setFilepath(filepath);
				qf.setQuestionNo(q.getQuestionNo());
				fileList.add(qf);
			}			
		}
		q.setFileList(fileList);
		List<QuestionFile> delFileList = questionService.updateQuestion(q,delFileNo);
		if(delFileList == null) {
			model.addAttribute("title","수정 실패");
			model.addAttribute("msg","처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/Question/questionList?type=1&reqPage=1");
			return "common/msg";
		}else {
			for(QuestionFile questionFile : delFileList) {
				File delFile = new File(savepath+questionFile.getFilepath());
				delFile.delete();
			}
			return "redirect:/Question/questionList?type=1&reqPage=1";
		}
	}
	
	@GetMapping(value="/deleteQuestion")
	public String deleteQuestion(int questionNo,Model model) {
		List<QuestionFile> delFileList = questionService.deleteQuestion(questionNo);
		String savepath = root+"/question/";
		if(delFileList ==null) {
			model.addAttribute("title","삭제 실패");
			model.addAttribute("msg","처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/Question/questionList?type=1&reqPage=1");
			return "common/msg";
		}else {
			for(QuestionFile questionFile : delFileList) {
				File delFile = new File(savepath+questionFile.getFilepath());
				delFile.delete();
			}
			return "redirect:/Question/questionList?type=1&reqPage=1";
		}
	}
	
	
	
	
	
}
