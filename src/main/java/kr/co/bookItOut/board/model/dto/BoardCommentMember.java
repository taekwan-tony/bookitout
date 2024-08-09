package kr.co.bookItOut.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardCommentMember {
	private int BoardCommentNo;
	private String memberId;
	private String memberImg;
}
