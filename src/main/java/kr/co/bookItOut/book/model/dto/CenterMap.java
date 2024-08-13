package kr.co.bookItOut.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CenterMap {
	private int mapNo;
	private String latitude;
	private String longitude;
	private int adminNo;
	private String adminName;
	private String adminAddr;
	private String adminPhone;
	private String openingDay;
}
