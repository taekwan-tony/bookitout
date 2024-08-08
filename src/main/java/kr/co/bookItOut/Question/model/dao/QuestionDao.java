package kr.co.bookItOut.Question.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionFile;
import kr.co.bookItOut.Question.model.dto.QuestionFileRowMapper;
import kr.co.bookItOut.Question.model.dto.QuestionRowMapper;

@Repository
public class QuestionDao {
	@Autowired
	private QuestionRowMapper questionRowMapper;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private QuestionFileRowMapper questionFileRowMapper;

	public int insertQuestion(Question q) {
		String query = "insert into customer_question values(customer_question_seq.nextval,?,?,?,null,?,?)";
		System.out.println(q);
		Object[] params = {q.getQuestionType(),q.getQuestionTitle(),q.getQuestionContent(),q.getMemberNo(),q.getQuestionEmail()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectQuestionNo(Question q) {
		String query = "select max(question_no) from customer_question";
		int questionNo = jdbc.queryForObject(query,Integer.class);

		
		return questionNo;
	}
	public int insertQuestionFile(QuestionFile qf) {
		String query ="insert into question_file values(question_file_seq.nextval,?,?,?)";
		Object[] params = {qf.getFilename(),qf.getFilepath(),qf.getQuestionNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	//select * from(select rownum as rnum, q.* from(select * from customer_question order by 1 desc)q
	public int selectTotalCount(int memberNo, int type) {
		String query = null;
		switch(type) {
		case 1 :
			query = "select count(*) from customer_question where member_no =?";
			break;
		case 2 : 
			query = "select count(*) from customer_question where member_no =? and question_answer is null";
			break;
		case 3 :
			query = "select count(*) from customer_question where member_no =? and question_answer is not null";
			break;
		}
		Object[] params = {memberNo};
		int totalCount = jdbc.queryForObject(query, Integer.class,params);
		return totalCount;
	}
	public List selectAllQuestion(int memberNo, int type, int start, int end) {
		String query = null;
		switch(type) {
		case 1 :
			query = "select * from(select rownum as rnum, q.* from(select * from customer_question where member_no =? order by 1 desc)q) where rnum between ? and ?";
			break;
		case 2 : 
			query = "select * from(select rownum as rnum, q.* from(select * from customer_question where member_no =? and question_answer is null order by 1 desc)q) where rnum between ? and ?";
			break;
		case 3 :
			query = "select * from(select rownum as rnum, q.* from(select * from customer_question where member_no =? and question_answer is not null order by 1 desc)q) where rnum between ? and ?";
			break;
		}
		Object[] params = {memberNo,start,end};
		List list = jdbc.query(query, questionRowMapper,params);
		return list;
	}
	public List selectAllFile(int questionNo) {
		String query = "select * from question_file where question_no =?";
		Object[] params = {questionNo};
		List fileList = jdbc.query(query, questionFileRowMapper,params);
		return fileList;
	}
	public Question selectOneQuestion(int questionNo) {
		String query = "select * from customer_question where question_no = ?";
		Object[] params = {questionNo};
		List<Question> list = jdbc.query(query,questionRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	public int updateQuestion(Question q) {
		String query = "update customer_question set question_type=?, question_title=?, question_content=?,question_email=? where question_no=?";
		Object[] params = {q.getQuestionType(),q.getQuestionTitle(),q.getQuestionContent(),q.getQuestionEmail(),q.getQuestionNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public QuestionFile selectOneQuestionFile(int fileNo) {
		String query ="select * from question_file where question_file_no=?";
		Object[] params = {fileNo};
		List<QuestionFile> list = jdbc.query(query, questionFileRowMapper,params);
		return list.get(0);
	}
	public int deletequestionFile(int fileNo) {
		String query ="delete from question_file where question_file_no =?";
		Object[] params = {fileNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteQuestion(int questionNo) {
		String query = "delete from customer_question where question_no=?";
		Object[] params = {questionNo};
		int result = jdbc.update(query,params);
		return result;
	}
	
}
