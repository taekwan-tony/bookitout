package kr.co.bookItOut.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.board.model.dao.BoardDao;
import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardComment;
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
	@Transactional
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

	public Board selectOneBoard(int boardNo, String check, int memberNo) {
		Board b=boardDao.selectOneBoard(boardNo);
		if(b!=null) {
			if(check==null) {
				int result=boardDao.updateReadCount(boardNo);
			}
			List fileList = boardDao.selectBoardFile(boardNo);
			b.setFileList(fileList);
			List<BoardComment> commentList = boardDao.selectCommentList(boardNo,memberNo);
			b.setCommentList(commentList);
			List reCommentList=boardDao.selectReCommentList(boardNo, memberNo);
			b.setReCommentList(reCommentList);
		}
		return b;
	}

	public BoardListData search(String type, String keyword, int reqPage) {
		int numPerPage = 10;
		int end = reqPage*numPerPage;
		int start = end - numPerPage + 1;
		List list=boardDao.searchBoard(type, keyword, start, end);
		int totalCount=boardDao.selectBoardTotalCount();
		int totalPage = 0;
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		int pageNaviSize = 10;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		String pageNavi = "<ul class='pagination circle-style'>";
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/board/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/board/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/board/list?reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-item' href='/board/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		pageNavi += "</ul>";
		BoardListData bld=new BoardListData(list, pageNavi);
		return bld;
	}
	@Transactional
	public List<BoardF‎ile> deleteBoard(int boardNo) {
		List list=boardDao.selectBoardFile(boardNo);
		int result= boardDao.deleteBoard(boardNo);
		if(result>0) {
			return list;
		}
		return null;
	}
	@Transactional
	public List<BoardF‎ile> updateBoard(Board b, List<BoardF‎ile> fileList, int[] delFileNo) {
		List<BoardF‎ile> delFileList = new ArrayList<BoardF‎ile>();
		int result = boardDao.updateBoard(b);
		if(result > 0) {
			for(BoardF‎ile boardFile : fileList) {
				result += boardDao.insertBoard(boardFile);
			}
			if(delFileNo != null) {
				for(int fileNo : delFileNo) {
					BoardF‎ile boardFile = boardDao.selectOneBoardFile(fileNo);
					delFileList.add(boardFile);
					result += boardDao.deleteBoardFile(fileNo);
				}
			}
		}
		int updateTotal = delFileNo==null?fileList.size()+1 : fileList.size()+1+delFileNo.length;
		if(updateTotal == result) {
			return delFileList;
		}else {
			return null;
		}
	}
	@Transactional
	public int insertComment(BoardComment bc) {
		int result =boardDao.insertComment(bc);
		return result;
	}
	@Transactional
	public int updateComment(BoardComment bc) {
		int result =boardDao.updateComment(bc);
		return result;
	}
	@Transactional
	public int deleteComment(BoardComment bc) {
		int result =boardDao.deleteComment(bc);
		return result;
	}
	@Transactional
	public int likePush(int boardCommentNo, int isLike, int memberNo) {
		// TODO Auto-generated method stub
		return 0;
	}
}
