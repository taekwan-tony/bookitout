package kr.co.bookItOut.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private int readCount;
	private String regDate;
	private String memberId;
	private List<BoardF‎ile> fileList;
	private List<BoardComment> commentList;
	private List<BoardComment> reCommentList;
}
