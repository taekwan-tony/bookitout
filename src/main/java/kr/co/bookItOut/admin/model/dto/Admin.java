package kr.co.bookItOut.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {
	private int adminNo;
	private String adminId;
	private String adminPw;
	private String adminName;
	private String openingDay;
	private String adminAddr;
	private String adminEmail;
	private String adminPhone;
	private int adminType;
	
}
