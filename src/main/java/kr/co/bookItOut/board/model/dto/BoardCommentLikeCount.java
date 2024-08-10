package kr.co.bookItOut.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardCommentLikeCount {
	private int boardCommentNo;
	private int memberNo;
	private int likeCount;
}
