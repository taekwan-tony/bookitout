package kr.co.bookItOut.centerInventory.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryBookRowMapper;
import kr.co.bookItOut.centerInventory.model.dto.CenterInventoryRowMapper;

@Repository
public class CenterInventoryDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private CenterInventoryRowMapper centerInventoryRowMapper;
	
	@Autowired
	private CenterInventoryBookRowMapper centerInventoryBookRowMapper;

	public List selectAllCenterInventory() {
		String query = "select admin_name, admin_addr, center_book_count from admin_tbl join center_inventory using (admin_no)";
		List list = jdbc.query(query, centerInventoryBookRowMapper);
		return list;
	}
}
