package kr.co.bookItOut.admin.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminRowMapper implements RowMapper<Admin>{

	@Override
	public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
		Admin ad = new Admin();
		ad.setAdminAddr(rs.getString("admin_addr"));
		ad.setAdminEmail(rs.getString("admin_email"));
		ad.setAdminId(rs.getString("admin_id"));
		ad.setAdminName(rs.getString("admin_name"));
		ad.setAdminNo(rs.getInt("admin_no"));
		ad.setAdminPw(rs.getString("admin_pw"));
		ad.setAdminType(rs.getInt("admin_type"));
		ad.setOpeningDay(rs.getString("opening_day"));
		ad.setAdminPhone(rs.getString("admin_phone"));
		
		return ad;
	}
	
}
