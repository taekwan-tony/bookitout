package kr.co.bookItOut.notice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.notice.dto.Notice;
import kr.co.bookItOut.notice.dto.NoticeData;
import kr.co.bookItOut.notice.dto.NoticeDataRowMapper;
import kr.co.bookItOut.notice.dto.NoticeRowMapper;

@Repository
public class NoticeDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private NoticeRowMapper noticeRowMapper;
	@Autowired
	private NoticeDataRowMapper noticeDataRowMapper;
	
	public List selectAllNotice(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from notice order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, noticeRowMapper,params);
		return list;
	}

	public int totalCount() {
		String query = "select count(*) from notice";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public Notice selectOneNotice(int noticeNo) {
		String query = "select * from notice where notice_no =?";
		Object[] params = {noticeNo};
		List list = jdbc.query(query, noticeRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Notice)list.get(0);
		}
	}

	public NoticeData selectTwoNotice(int noticeNo) {
		String query = "select * from(select notice_no,lag(notice_no, 1, 0) over(order by notice_no desc) prev_no,lag(notice_title, 1, '이전글이 없습니다.') over (order by notice_no desc) prev_subject,lead(notice_no,1,0) over (order by notice_no desc) next_no,lead(notice_title,1,'다음글이 없습니다.') over (order by notice_no desc) next_subject from notice order by 1 desc) where notice_no=?";
		Object[] params = {noticeNo};
		List list = jdbc.query(query, noticeDataRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (NoticeData)list.get(0);
		}
	}

	public int readCount(int noticeNo) {
		String query = "update notice set read_count=read_count+1 where notice_no=?";
		Object[] params = {noticeNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertNotice(Notice n) {
		String query = "insert into notice values(notice_seq.nextval,?,to_char(sysdate,'YYYY-MM-DD'),?,0)";
		Object[] params = {n.getNoticeTitle(),n.getNoticeContent()};
		int result = jdbc.update(query,params);
		
		
		return result;
	}

	public int updateNotice(Notice n) {
		String query = "update notice set notice_title=?,notice_content=?,read_count = read_count-1 where notice_no=?";
		Object[] params = {n.getNoticeTitle(),n.getNoticeContent(),n.getNoticeNo()};
		int result = jdbc.update(query,params);
		System.out.println("DAO result : "+result);
		System.out.println("DAO : "+n.getNoticeNo());
		return result;
	}

	public int deleteNotice(int noticeNo) {
		String query = "delete from notice where notice_no =?";
		Object[] params = {noticeNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public List selectAllNotice(int start, int end, String keyword) {
		String query = "select * from (select rownum as rnum, n.* from (select * from notice where notice_title like '%'||?||'%' or notice_content='%'||?||'%' order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {keyword,keyword,start,end};
		List list = jdbc.query(query, noticeRowMapper,params);
		return list;
	}
	
	
}
