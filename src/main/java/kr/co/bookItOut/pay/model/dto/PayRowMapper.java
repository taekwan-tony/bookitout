package kr.co.bookItOut.pay.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PayRowMapper implements RowMapper<Pay>{

	@Override
	public Pay mapRow(ResultSet rs, int rowNum) throws SQLException{
		Pay p = new Pay();
		p.setPayNo(rs.getInt("pay_no"));
		p.setPayMethod(rs.getString("pay_method"));
		p.setMemberNo(rs.getInt("member_no"));
		p.setPayDate(rs.getString("pay_date"));
		p.setTotalPrice(rs.getInt("total_price"));
		return p;
	}
}
