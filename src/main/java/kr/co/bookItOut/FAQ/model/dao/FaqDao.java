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
	
	
	
	public List selectAllFaq(int type, int reqpage) {
		String query = "select * from(select rownum as rnum, f.* from (select * from faq order by faq_read_count desc)f) where  rnum between 1 and 10";
		List list = jdbc.query(query, faqRowMapper);
		
		return list;
	}
}
