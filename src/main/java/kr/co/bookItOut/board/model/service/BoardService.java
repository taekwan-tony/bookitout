package kr.co.bookItOut.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.board.model.dao.BoardDao;
import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardF‎ile;
import kr.co.bookItOut.board.model.dto.BoardListData;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public BoardListData selectBoardList(int reqPage) {
		int numPerPage=10;
		int end=reqPage*numPerPage;
		int start=end-numPerPage+1;
		List list=boardDao.selectBoardList(start, end);
		int totalCount= boardDao.selectBoardTotalCount();
		int totalPage=0;
		if(totalCount%numPerPage==0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		int pageNaviSize=10;
		int pageNo=((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		String pageNavi = "<ul class='pagination circle-style'>";
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/board/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/notice/list?reqPage="+pageNo+"'>";
			}
			
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/notice/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		BoardListData boardListData=new BoardListData(list,pageNavi);
			return boardListData;
	}

	public int insertBoard(Board b, List<BoardF‎ile> fileList) {
		int result = boardDao.insertBoard(b);
		if(result > 0) {
			int boardNo=boardDao.selectBoardNo();
			for(BoardF‎ile boardF‎ile: fileList) {
				boardF‎ile.setBoardNo(boardNo);
				result+=boardDao.insertBoard(boardF‎ile);
			}
		}
		return result;
	}
}
