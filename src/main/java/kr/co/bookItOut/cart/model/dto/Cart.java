package kr.co.bookItOut.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
	private int orderNo;
	private int orderQuntity;
	private String orderDate;
	private int orderCheck;
	private int adminNo;
	private int bookNo;
}
