package kr.co.bookItOut.centerInventory.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CenterInventoryRowMapper implements RowMapper<CenterInventory> {

	@Override
	public CenterInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
		CenterInventory ci = new CenterInventory();
		ci.setCenterBookNo(rs.getInt("center_book_no"));
		ci.setCenterBookCount(rs.getInt("center_book_count"));
		ci.setBookNo2(rs.getInt("book_no2"));
		ci.setAdminNo(rs.getInt("admin_no"));
		return ci;
	}
	
}
