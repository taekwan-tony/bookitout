package kr.co.bookItOut.centerInventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CenterInventory {
	private int centerBookNo;
	private int centerBookCount;
	private int bookNo2;
	private int adminNo;
}