package kr.co.bookItOut.book.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CenterMapRowMapper implements RowMapper<CenterMap> {

	@Override
	public CenterMap mapRow(ResultSet rs, int rowNum) throws SQLException {
		CenterMap cm = new CenterMap();
		cm.setMapNo(rs.getInt("map_no"));
		cm.setLatitude(rs.getString("latitude"));
		cm.setLongitude(rs.getString("longitude"));
		cm.setAdminNo(rs.getInt("admin_no"));
		cm.setAdminName(rs.getString("admin_name"));
		cm.setAdminAddr(rs.getString("admin_addr"));
		return cm;
	}
	
}
