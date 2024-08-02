package kr.co.bookItOut.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notice {
	private int noticeNo;
	private String noticeTitle;
	private String writeDate;
	private String noticeContent;
	private int readCount;
	
}
