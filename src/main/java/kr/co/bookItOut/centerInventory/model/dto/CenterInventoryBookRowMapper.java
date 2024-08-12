package kr.co.bookItOut.centerInventory.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CenterInventoryBookRowMapper implements RowMapper<CenterInventoryBook> {

	@Override
	public CenterInventoryBook mapRow(ResultSet rs, int rowNum) throws SQLException {
		CenterInventoryBook cib = new CenterInventoryBook();
		cib.setAdminName(rs.getString("admin_name"));
		cib.setAdminAddr(rs.getString("admin_addr"));
		cib.setAdminNo(rs.getInt("admin_no"));
		cib.setCenterBookCount(rs.getInt("center_book_count"));
		cib.setBookName(rs.getString("book_name"));
		
		//cib.setBookNo(rs.getInt("book_no"));
		
		return cib;
	}
	
}