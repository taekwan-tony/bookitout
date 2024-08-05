package kr.co.bookItOut.FAQ.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.FAQ.model.dto.Faq;
import kr.co.bookItOut.FAQ.model.dto.FaqRowMapper;

@Repository
public class FaqDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private FaqRowMapper faqRowMapper;
	
	
	
	public List selectAllFaq(String faqType, int reqpage,int start,int end) {
		String query = "select * from(select rownum as rnum, f.* from (select * from faq where faq_type like '%'||?||'%' order by faq_no desc)f) where  rnum between ? and ?";
		Object[] params = {faqType,start,end};
		List list = jdbc.query(query, faqRowMapper,params);
		
		return list;
	}



	public int totalCountBoard(String faqType) {
		String query = "select count(*) from faq where faq_type like '%'||?||'%'";
		Object[] params = {faqType};
		int totalCount = jdbc.queryForObject(query, Integer.class,params);
		return totalCount;
	}
}
