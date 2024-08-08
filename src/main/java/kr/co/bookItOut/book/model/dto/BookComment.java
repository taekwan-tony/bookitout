package kr.co.bookItOut.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookComment {
	 private int bookCommentNo;
	 private int bookNo;
	 private int memberNo;
	 private String bookCommentContent;
	 private String bookCommentDate;
}
