package kr.co.bookItOut.pay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pay {
	private int payNo;
	private String payMethod;
	private int memberNo;
	private String payDate;
	private int totalPrice;
}
