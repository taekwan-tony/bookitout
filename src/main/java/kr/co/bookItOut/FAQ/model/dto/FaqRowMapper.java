package kr.co.bookItOut.FAQ.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FaqRowMapper implements RowMapper<Faq>{

	@Override
	public Faq mapRow(ResultSet rs, int rowNum) throws SQLException {
		Faq f = new Faq();
		f.setFaqContent(rs.getString("faq_content"));
		f.setFaqNo(rs.getInt("faq_no"));
		f.setFaqTitle(rs.getString("faq_title"));
		f.setFaqType(rs.getString("faq_type"));
		f.setFaqReadCount(rs.getInt("faq_read_count"));
		return f;
	}
	
}
