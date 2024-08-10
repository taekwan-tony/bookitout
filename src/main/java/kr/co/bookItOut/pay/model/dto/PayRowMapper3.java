package kr.co.bookItOut.pay.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PayRowMapper3 implements RowMapper<Pay>{

	@Override
	public Pay mapRow(ResultSet rs, int rowNum) throws SQLException{
		Pay p = new Pay();
		p.setBookName(rs.getString("book_name"));
		return p;
	}
}
