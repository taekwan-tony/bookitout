package kr.co.bookItOut.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookComment {
	 private int bookCommentNo;
	 private String bookCommentWriter;
	 private String bookCommentContent;
	 private String bookCommentDate;
	 private int bookRef;/* bookNo */
	 private int bookCommentRef;
	 private int likeCount;
	 private int isLike;
	 
	 private String bookImg;
	 private String bookName;
}
