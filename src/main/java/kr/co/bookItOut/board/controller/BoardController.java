package kr.co.bookItOut.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardF‎ile;
import kr.co.bookItOut.board.model.dto.BoardListData;
import kr.co.bookItOut.board.model.service.BoardService;
import kr.co.bookItOut.util.FileUtils;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/list")
	public String list(Model model, int reqPage) {
		BoardListData bld = boardService.selectBoardList(reqPage);
		model.addAttribute("list" ,bld.getList());
		model.addAttribute("pageNavi" ,bld.getPageNavi());
		return "board/list";
	}
	@GetMapping(value="/writeFrm")
	public String writeFrm() {
		return "board/writeFrm";
	}
	@GetMapping(value="/editorFrm")
	public String editorFrm() {
		return "board/editorFrm";
	}
	@PostMapping(value="/write")
	public String writer(Board b, MultipartFile[] upfile, Model model) {
		List<BoardF‎ile> fileList = new ArrayList<BoardF‎ile>();
		if(!upfile[0].isEmpty()) {
			String savepath = root+"/board/";
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				BoardF‎ile boardFile = new BoardF‎ile();
				boardFile.setFilename(filename);
				boardFile.setFilepath(filepath);
				fileList.add(boardFile);
			}
		
		}
		int result = boardService.insertBoard(b,fileList);
		if(result > 0) {
			model.addAttribute("title","작성성공!");
			model.addAttribute("msg", "게시물 작성에 성공했습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/board/list?reqPage=1");
			return "common/msg";
		}
		return "redirect:/board/writeFrm";
	}
}
