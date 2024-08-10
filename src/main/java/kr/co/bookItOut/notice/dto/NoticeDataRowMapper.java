package kr.co.bookItOut.notice.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NoticeDataRowMapper implements RowMapper<NoticeData>{

	@Override
	public NoticeData mapRow(ResultSet rs, int rowNum) throws SQLException {
		NoticeData nd = new NoticeData();
		nd.setNextno(rs.getInt("next_no"));
		nd.setNextSubject(rs.getString("next_subject"));
		nd.setNoticeNo(rs.getInt("notice_no"));
		nd.setPrevno(rs.getInt("prev_no"));
		nd.setPrevSubject(rs.getString("prev_subject"));
		return nd;
	}
	
}
