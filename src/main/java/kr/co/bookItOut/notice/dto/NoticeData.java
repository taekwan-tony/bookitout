package kr.co.bookItOut.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeData {
	private int noticeNo;
	private int prevno;
	private String prevSubject;
	private int nextno;
	private String nextSubject;
	
}
