package kr.co.bookItOut.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
	private int cartNo;
	private int bookNo;
	private int bookCartCount;//구매 수량
	private int memberNo;
	private String bookImg;
	private String bookName;
	private int bookPrice;
}
