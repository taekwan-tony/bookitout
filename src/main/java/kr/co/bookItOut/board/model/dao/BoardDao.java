package kr.co.bookItOut.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.board.model.dto.Board;
import kr.co.bookItOut.board.model.dto.BoardComment;
import kr.co.bookItOut.board.model.dto.BoardCommentLike;
import kr.co.bookItOut.board.model.dto.BoardCommentLikeCount;
import kr.co.bookItOut.board.model.dto.BoardCommentLikeCountRowMapper;
import kr.co.bookItOut.board.model.dto.BoardCommentLikeRowMapper;
import kr.co.bookItOut.board.model.dto.BoardCommentMember;
import kr.co.bookItOut.board.model.dto.BoardCommentMemberRowMapper;
import kr.co.bookItOut.board.model.dto.BoardCommentRowMapper;
import kr.co.bookItOut.board.model.dto.BoardFileRowMapper;
import kr.co.bookItOut.board.model.dto.BoardF‎ile;
import kr.co.bookItOut.board.model.dto.BoardRowMapper;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.member.model.dto.MemberRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	@Autowired
	private BoardCommentRowMapper boardCommentRowMapper;
	@Autowired
	private BoardCommentMemberRowMapper boardCommentMemberRowMapper;
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired
	private BoardCommentLikeRowMapper boardCommentLikeRowMapper; 

	public List selectBoardList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from board order by 1 desc)n) where rnum between ? and ?";
		Object[] params = { start, end };
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}

	public int selectBoardTotalCount() {
		String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public int insertBoard(Board b) {
		String query = "insert into board values(board_seq.nextval,?,?,?,0,to_char(sysdate,'YYYY-MM-DD'))";
		Object[] params = { b.getBoardTitle(), b.getBoardContent(), b.getBoardWriter()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectBoardNo() {
		String query = "select max(board_no) from board";
		int boardNo = jdbc.queryForObject(query, Integer.class);
		return boardNo;
	}

	public int insertBoardFile(BoardF‎ile boardF‎ile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?,?)";
		Object[] params = { boardF‎ile.getBoardNo(), boardF‎ile.getFilename(), boardF‎ile.getFilepath() };
		int result = jdbc.update(query, params);
		return result;
	}

	public Board selectOneBoard(int boardNo) {
		String query = "select * from board where board_no=?";
		Object[] params = { boardNo };
		List list = jdbc.query(query, boardRowMapper, params);
		if (list.isEmpty()) {
			return null;
		} else {
			return (Board) list.get(0);
		}
	}

	public int updateReadCount(int boardNo) {
		String query = "update board set read_count = read_count+1 where board_no=?";
		Object[] params = { boardNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectBoardFile(int boardNo) {
		String query = "select * from board_file where board_no=?";
		Object[] params = { boardNo };
		List list = jdbc.query(query, boardFileRowMapper, params);
		return list;
	}

	public List<BoardComment> selectCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,\r\n"
				+ "(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,\r\n"
				+ "(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like\r\n"
				+ "from board_comment bc\r\n" + "where board_ref=? and board_comment_ref is null order by 1";
		Object[] params = { memberNo, boardNo };
		List list = jdbc.query(query, boardCommentRowMapper, params);
		return list;
	}

	public List selectReCommentList(int boardNo, int memberNo) {
		String query = "select bc.*,\r\n"
				+ "(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no) as like_count,\r\n"
				+ "(select count(*) from board_comment_like where board_comment_no=bc.board_comment_no and member_no=?) as is_like\r\n"
				+ "from board_comment bc\r\n" + "where board_ref=? and board_comment_ref is not null order by 1";
		Object[] params = { memberNo, boardNo };
		List list = jdbc.query(query, boardCommentRowMapper, params);
		return list;
	}

	public List searchBoard(String type, String keyword, int start, int end, String option) {
		String query = "";
		if(type.equals("1")) {
			if(option.equals("new")) {				
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' or board_writer like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
			}else if(option.equals("old")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' or board_writer like '%'||?||'%' order by 1)n) where rnum between ? and ? ";
			}else if(option.equals("readCount")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' or board_writer like '%'||?||'%' order by read_count desc)n) where rnum between ? and ? ";
			}/*else if(option.equals("comment")){
				query = "SELECT board.BOARD_NO from board join BOARD_COMMENT on (BOARD.BOARD_NO=BOARD_COMMENT.BOARD_REF) GROUP BY BOARD.BOARD_NO order by COUNT(*) desc";
				List<Board> list = jdbc.query(query, boardRowMapper);
				List<Integer> noList=new ArrayList<Integer>(); 
				for (int i = 0; i < list.size(); i++) {
					noList.set(i,list.get(i).getBoardNo());					
				}
				for (int i : noList) {
					String que="SELECT * FROM BOARD WHERE BOARD_NO=?";
					Object[] params = {i};
					List finList = jdbc.query(query, boardCommentRowMapper, params);
				}
				return list;
			}*/
			Object[] params = { keyword, keyword, keyword, start, end };
			List list = jdbc.query(query, boardRowMapper, params);
			return list;
		} else if (type.equals("2")) {
			if(option.equals("new")) {	
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_title like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
			}else if(option.equals("old")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_title like '%'||?||'%' order by 1)n) where rnum between ? and ? ";
			}else if(option.equals("readCount")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_title like '%'||?||'%' order by read_count desc)n) where rnum between ? and ? ";
			}/*else if(option.equals("comment"))){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_title like '%'||?||'%' order by board_title)n) where rnum between ? and ? ";
			}*/
		} else if (type.equals("3")) {
			if(option.equals("new")) {	
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_writer like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
			}else if(option.equals("old")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_writer like '%'||?||'%' order by 1)n) where rnum between ? and ? ";
			}else if(option.equals("readCount")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_writer like '%'||?||'%' order by read_count desc)n) where rnum between ? and ? ";
			}/*else if(option.equals("comment")){
				query = "SELECT board.BOARD_NO from board join BOARD_COMMENT on (BOARD.BOARD_NO=BOARD_COMMENT.BOARD_REF) GROUP BY BOARD.BOARD_NO order by COUNT(*) desc";
			}*/
		} else if (type.equals("4")) {
			if(option.equals("new")) {	
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
			}else if(option.equals("old")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' order by 1)n) where rnum between ? and ? ";
			}else if(option.equals("readCount")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' order by read_count desc)n) where rnum between ? and ? ";
			}/*else if(option.equals("comment"))){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' or board_title like '%'||?||'%' order by board_title)n) where rnum between ? and ? ";
			}*/	
			Object[] params = { keyword, keyword, start, end };
			List list = jdbc.query(query, boardRowMapper, params);
			return list;
		} else if (type.equals("5")) {
			if(option.equals("new")) {	
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' order by 1 desc)n) where rnum between ? and ? ";
			}else if(option.equals("old")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' order by 1)n) where rnum between ? and ? ";
			}else if(option.equals("readCount")){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' order by read_count desc)n) where rnum between ? and ? ";
			}/*else if(option.equals("comment"))){
				query = "select * from (select rownum as rnum, n.* from (select * from board where board_content like '%'||?||'%' order by board_content)n) where rnum between ? and ? ";
			}*/
		}
		Object[] params = { keyword, start, end };
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}

	public int deleteBoard(int boardNo) {
		String query = "delete from board where board_no=?";
		Object[] params = { boardNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateBoard(Board b) {
		String query = "update board set board_title=?, board_content=?, read_count = read_count-1 where board_no=?";
		Object[] params = { b.getBoardTitle(), b.getBoardContent(), b.getBoardNo() };
		int result = jdbc.update(query, params);
		return result;
	}

	public BoardF‎ile selectOneBoardFile(int fileNo) {
		String query = "select * from board_file where file_no=?";
		Object[] params = { fileNo };
		List list = jdbc.query(query, boardFileRowMapper, params);
		return (BoardF‎ile) list.get(0);
	}

	public int deleteBoardFile(int fileNo) {
		String query = "delete from board_file where file_no=?";
		Object[] params = { fileNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public int insertComment(BoardComment bc) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,?,to_char(sysdate,'YYYY-MM-DD'),?,?)";
		String boardCommentRef = bc.getBoardCommentRef() == 0 ? null : String.valueOf(bc.getBoardCommentRef());
		Object[] params = { bc.getBoardCommentWriter(), bc.getBoardCommentContent(), bc.getBoardRef(), boardCommentRef };
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateComment(BoardComment bc) {
		String query = "update board_comment set board_comment_content=? where board_comment_no=?";
		Object[] params = { bc.getBoardCommentContent(), bc.getBoardCommentNo() };
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteComment(BoardComment bc) {
		String query = "delete from board_comment where board_comment_no=?";
		Object[] params = { bc.getBoardCommentNo() };
		int result = jdbc.update(query, params);
		return result;
	}

	public int insertBoardCommentLike(int boardCommentNo, int memberNo) {
		String query = "insert into board_comment_like values(?,?)";
		Object[] params = { boardCommentNo, memberNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteBoardCommentLike(int boardCommentNo, int memberNo) {
		String query = "delete from board_comment_like where board_comment_no=? and member_no=?";
		Object[] params = { boardCommentNo, memberNo };
		int result = jdbc.update(query, params);
		return result;
	}
	public int selectBoardCommentIsLike(int boardCommentNo, int memberNo) {
		String query = "select count(*) from board_comment_like where board_comment_no=? and member_no=?";
		Object[] params = { boardCommentNo, memberNo };
		int isLike = jdbc.queryForObject(query, Integer.class, params);
		return isLike;
	}
	public int selectBoardCommentLikeCount(int boardCommentNo) {
		String query = "select count(*) from board_comment_like where board_comment_no=?";
		Object[] params = { boardCommentNo };
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}

	public List selectCommentWriterMemberList(int boardNo) {
		String query = "select board_comment.BOARD_COMMENT_NO,MEMBER_TBL.MEMBER_Id,MEMBER_TBL.MEMBER_IMG from MEMBER_TBL join board_comment on(board_comment.BOARD_COMMENT_WRITER=MEMBER_TBL.MEMBER_ID) where board_ref=?";
		Object[] params = {boardNo};
		List list = jdbc.query(query, boardCommentMemberRowMapper, params);
		return(List<BoardCommentMember>)list;
	}

	public List<Member> selectAllMemberList() {
		String query = "SELECT * FROM MEMBER_TBL";
		List list = jdbc.query(query, memberRowMapper);
		return (List<Member>)list;
	}

	public List<BoardCommentLike> selectBoardCommentLikeList() {
		String query = "select * from board_comment_like order by BOARD_COMMENT_NO"; 
		List list = jdbc.query(query, boardCommentLikeRowMapper);
		return (List<BoardCommentLike>) list;
	}

	public int selectLikeCount(int boardCommentNo) {
		String query = "select COUNT(*) from board_comment_like GROUP BY BOARD_COMMENT_NO HAVING BOARD_COMMENT_NO=?";
		Object[] params = {boardCommentNo};
		int isLike = jdbc.queryForObject(query, Integer.class, params);
		return isLike;
	}
	public List<BoardComment> selectBoardComment() {
		String query = "select * from board_comment";
		List list = jdbc.query(query, boardCommentRowMapper);
		return (List<BoardComment>) list;
	}

	public List<Member> selectAllMember() {
		String query = "select * from member_tbl";
		List list = jdbc.query(query, memberRowMapper);
		return (List<Member>)list;
	}

	public int selectCommentCount(int boardCommentNo, int memberNo) {
		String query = "SELECT COUNT(*) FROM BOARD_COMMENT_LIKE WHERE BOARD_COMMENT_NO=? and MEMBER_NO=?";
		Object[] params = {boardCommentNo, memberNo};
		int result = jdbc.queryForObject(query, Integer.class, params);
		return result;
	}

	public int selectBoardSearchCount(String type, String keyword) {
		String query = "";
		if(type.equals("1")) {
			query = "select count(*) from board";
		}else if (type.equals("2")) {
			query = "select count(*) from board where board_title like '%'||?||'%'";
			Object[] params = { keyword };
			int result=jdbc.queryForObject(query, Integer.class, params);
			return result;
		}else if (type.equals("3")) {
			query = "select count(*) from board where board_writer like '%'||?||'%'";
			Object[] params = { keyword };
			int result = jdbc.queryForObject(query, Integer.class, params);
			return result;
		}else if (type.equals("4")) {
			query = "select count(*) from board where board_content like '%'||?||'%' or board_title like '%'||?||'%'";
			Object[] params = { keyword, keyword };
			int result = jdbc.queryForObject(query, Integer.class, params);
			return result;
		} else if (type.equals("5")) {
			query = "select count(*) from board where board_content like '%'||?||'%'";
			Object[] params = { keyword };
			int result=jdbc.queryForObject(query, Integer.class, params);
			return result;
		}
		int result=jdbc.queryForObject(query, Integer.class);
		return result;
	}
}
