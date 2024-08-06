package kr.co.bookItOut.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookContent {
	 private int bookCommentNo;
	 private String boardCommentContent;
	 private String boardCommentDate;
	 private int boardNoRef;
	 private int bookNo;
	 private int memberNo;
}
