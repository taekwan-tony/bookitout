package kr.co.bookItOut.book.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookListData {
	private List list;
	private String pageNavi;
	

}
