package kr.co.bookItOut.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminOrderBook {
	private int bookNo;
	private String bookName;
	private String bookWriter;
	private int bookPrice;
	private String bookType;
	private String bookGenre;
	private String publicationDate;
	private int orderNo;
	private String orderQuntity;
	private String orderDate;
	private int orderCheck;
	private int adminNo;
	private int adminType;
}
