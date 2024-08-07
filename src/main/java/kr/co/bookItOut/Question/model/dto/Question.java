package kr.co.bookItOut.Question.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {
	private int questionNo;
	private String questionType;
	private String questionTitle;
	private String questionContent;
	private String questionAnswer;
	private int memberNo;
	private String questionEmail;
	private List<QuestionFile> fileList;
}
