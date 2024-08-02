package kr.co.bookItOut.FAQ.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Faq {
	private int faqNo;
	private String faqType;
	private String faqTitle;
	private String faqContent;
	private int faqReadCount;
}
