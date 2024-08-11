package kr.co.bookItOut.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminCenterBook {
	private int centerBookNo;
	private int centerBookCount;
	private int bookNo;
	private int adminNo;
	private String bookName;
	private String bookWriter;
	private String bookType;
	private String bookGenre;
	private int bookPrice;
	private String bookPublisher; 
	private String enrollDate;
	private String publicationDate;
}
