package kr.co.bookItOut.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardComment;
import kr.co.bookItOut.board.model.dto.BoardF‎ile;
import kr.co.bookItOut.board.model.dto.BoardListData;
import kr.co.bookItOut.board.model.service.BoardService;
import kr.co.bookItOut.util.FileUtils;
import kr.co.bookItOut.member.model.dto.Member;

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
	@ResponseBody
	@PostMapping(value="/editorImage", produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root+"/board/editor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/board/editor/"+filepath;
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
		/*if(result > 0) {
			model.addAttribute("title","작성성공!");
			model.addAttribute("msg", "게시물 작성에 성공했습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/board/list?reqPage=1");
			return "common/msg";
		}*/
		return "redirect:/board/list?reqPage=1";
	}
	@GetMapping(value="/view")
	public String view(int boardNo, String check, Model model, @SessionAttribute(required = false) Member member) {
		int memberNo = 0;
		if(member != null) {
			memberNo = member.getMemberNo();
		}
		Board b = boardService.selectOneBoard(boardNo,check,memberNo);
		if(b == null) {
			model.addAttribute("title", "조회실패");
			model.addAttribute("msg", "해당 게시글이 존재하지 않습니다");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/board/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("b", b);
			return "board/view";
		}
	}
	@GetMapping(value="/filedown")
	public void filedown(BoardF‎ile boardFile, HttpServletResponse response) {
		String savepath = root + "/board/";
		fileUtils.downloadFile(savepath, boardFile.getFilename(),boardFile.getFilepath(),response);
	}
	@GetMapping(value="/delete")
	public String delete(int boardNo, Model model) {
		List<BoardF‎ile> list = boardService.deleteBoard(boardNo);
		if(list == null) {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("msg", "존재하지 않는 게시물입니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/board/list?reqPage=1");
		}else {
			String savepath = root+"/board/";
			for(BoardF‎ile file : list) {
				File delFile = new File(savepath+file.getFilepath());
				delFile.delete();
			}
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/board/list?reqPage=1");
		}
		return "common/msg";
	}
	@GetMapping(value="/updateFrm")
	public String updateFrm(int boardNo, Model model) {
		Board b = boardService.getOneBoard(boardNo);
		model.addAttribute("b", b);
		return "board/updateFrm";
	}
	@PostMapping(value="/update")
	public String update(Board b, MultipartFile[] upfile, int[] delFileNo, Model model) {
		List<BoardF‎ile> fileList = new ArrayList<BoardF‎ile>();
		String savepath = root+"/board/";
		if(!upfile[0].isEmpty()) {
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, file);
				BoardF‎ile boardFile = new BoardF‎ile();
				boardFile.setFilename(filename);
				boardFile.setFilepath(filepath);
				boardFile.setBoardNo(b.getBoardNo());
				fileList.add(boardFile);
			}
		}
		List<BoardF‎ile> delFileList = boardService.updateBoard(b,fileList,delFileNo);
		if(delFileList == null) {
			model.addAttribute("title", "수정 실패");
			model.addAttribute("msg", "처리중 문제가 발생했습니다. 잠시후 다시 시도해 주세요");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/board/list?reqPage=1");
			return "common/msg";
		}else {
			for(BoardF‎ile boardFile : delFileList) {
				File delFile = new File(savepath+boardFile.getFilepath());
				delFile.delete();
			}
			return "redirect:/board/view?boardNo="+b.getBoardNo();
		}
	}
	@PostMapping(value="/insertComment")
	public String insertComment(BoardComment bc, Model model) {
		int result = boardService.insertComment(bc);
	/*	if(result > 0) {	
		model.addAttribute("title", "댓글작성");
		model.addAttribute("msg", "댓글이 작성되었습니다");
		model.addAttribute("icon", "success");
	}else {
		model.addAttribute("title", "댓글작성 실패");
		model.addAttribute("msg", "댓글이 작성중 문제가 생겼습니다");
		model.addAttribute("icon", "warning");
	}
		model.addAttribute("loc", "/board/view?boardNo="+bc.getBoardRef());
		return "common/msg";*/
		return "redirect:/board/view?boardNo="+bc.getBoardRef();
	}
	@PostMapping(value="/updateComment")
	public String updateComment(BoardComment bc, Model model) {
		int result = boardService.updateComment(bc);
		/*if(result > 0) {
			model.addAttribute("title", "성공");
			model.addAttribute("msg", "댓글이 수정되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "잠시후 다시 시도해주세요.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc", "/board/view?check=1&boardNo="+bc.getBoardRef());*/
		return "redirect:/board/view?boardNo="+bc.getBoardRef();
	}
	@GetMapping(value="/deleteComment")
	public String deleteComment(BoardComment bc, Model model) {
		int result = boardService.deleteComment(bc);
		/*if(result > 0) {
			model.addAttribute("title", "성공");
			model.addAttribute("msg", "댓글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
		}else {
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "잠시후 다시 시도해주세요.");
			model.addAttribute("icon", "warning");
		}
		model.addAttribute("loc", "/board/view?check=1&boardNo="+bc.getBoardRef());*/
		return "redirect:/board/view?boardNo="+bc.getBoardRef();
	}
	
	@ResponseBody
	@PostMapping(value="/likePush")
	public int likePush(int boardCommentNo, int isLike, @SessionAttribute(required = false) Member member) {
		if(member == null) {
			return -10;
		}else {
			int memberNo = member.getMemberNo();
			int result = boardService.likePush(boardCommentNo,isLike,memberNo);
			return result;
		}
	}
	@GetMapping(value="/search")
	public String search(String type, String option, String keyword, int reqPage, Model model) {
		BoardListData bld  = boardService.search(type,keyword,reqPage,option);
		model.addAttribute("list" ,bld.getList());
		model.addAttribute("pageNavi" ,bld.getPageNavi());
		model.addAttribute("type" ,type);
		model.addAttribute("keyword" ,keyword);
		model.addAttribute("option" ,option);
		return "board/list";
	}
}
