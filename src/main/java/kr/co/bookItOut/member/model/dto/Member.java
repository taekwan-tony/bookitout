package kr.co.bookItOut.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberGender;
	private int memberAge;
	private String memberAddr;
	private String memberPhone;
	private String memberImg;
	private String enrollDate;
	private String memberMail;
	
}
