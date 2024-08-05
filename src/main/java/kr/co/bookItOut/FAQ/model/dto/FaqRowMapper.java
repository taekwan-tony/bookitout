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
		f.setFaqContent(rs.getString("FAQ_CONTENT"));
		f.setFaqNo(rs.getInt("FAQ_NO"));
		f.setFaqTitle(rs.getString("FAQ_TITLE"));
		f.setFaqType(rs.getString("FAQ_TYPE"));
		return f;
	}
	
}
