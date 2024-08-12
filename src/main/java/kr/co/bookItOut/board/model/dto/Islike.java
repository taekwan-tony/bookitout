package kr.co.bookItOut.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Islike {
	private int boardCommentNo;
	private int memberNo;
	private int isLike;
}
