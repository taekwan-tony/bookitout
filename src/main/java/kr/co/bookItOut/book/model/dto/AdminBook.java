package kr.co.bookItOut.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//센터수량 가져오기위한 변수
public class AdminBook {
	private int bookNo;
	private String bookName;
	private String bookWriter;
	private int bookPrice;
	private String bookPublisher;
	private String publicationDate;
	private String enrollDate;
	private String bookImg;
	private int adminNo;
	private String bookDetailContent;
	private String bookDetailWriter;
	private String bookDetailImg;
	private String bookType;
	private String bookGenre;
	private int readCount;
	private int centerBookCount;

}
