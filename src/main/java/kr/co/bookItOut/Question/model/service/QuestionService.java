package kr.co.bookItOut.Question.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.Question.model.dao.QuestionDao;
import kr.co.bookItOut.Question.model.dto.Question;
import kr.co.bookItOut.Question.model.dto.QuestionFile;
import kr.co.bookItOut.Question.model.dto.QuestionListData;

@Service
public class QuestionService {
	@Autowired
	private QuestionDao questionDao;

	@Transactional
	public int insertQuestion(Question q, List<QuestionFile> fileList) {
		int result = questionDao.insertQuestion(q);
		if(result>0) {
			int questionNo = questionDao.selectQuestionNo(q);
			for(QuestionFile qf : fileList) {
				qf.setQuestionNo(questionNo);;
				result+= questionDao.insertQuestionFile(qf);
			}
		}
		return result;
	}

	public QuestionListData selectAllQuestion(int memberNo, int type, int reqPage) {
		
		int numPerPage = 10;
		
		int end = reqPage*numPerPage;
		int start = end - numPerPage +1;
		
		int totalCount = questionDao.selectTotalCount(memberNo,type);
		
		List list = questionDao.selectAllQuestion(memberNo,type,start,end);
		int totalPage = 0;
		if(totalCount%numPerPage ==0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage + 1;
		}
		int pageNaviSize = 5;
		int pageNo = (reqPage-1)/pageNaviSize*pageNaviSize + 1;
		String pageNavi = "<div class='pagination'><ul>";
		if(pageNo !=1) {
			pageNavi += "<li>";
			pageNavi += "<a class ='page-item page-btn' href='/question/questionList?type="+type+"&reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span><i class='fa-solid fa-angle-left'></i></span>";
			pageNavi += "</a></li>";
		}
		for(int i =0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo==reqPage) {
				pageNavi += "<a class ='page-item active-page' href='/question/questionList?type="+type+"&reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class ='page-item' href='/question/questionList?type="+type+"&reqPage="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class ='page-item page-btn' href='/question/questionList?type="+type+"&reqPage="+pageNo+"'>";
			pageNavi += "<span><i class='fa-solid fa-angle-right'></i></span>";
			pageNavi += "</a></li>";
		}
		pageNavi +="</ul>";
		pageNavi +="</ul></div>";
		QuestionListData qld = new QuestionListData(list, pageNavi);
		return qld;
	}

	public List selectAllFile(int questionNo) {
		List fileList = questionDao.selectAllFile(questionNo);
		return fileList;
	}

	public Question selectOneQuestion(int questionNo) {
		Question q = questionDao.selectOneQuestion(questionNo);
		return q;
	}
	
	@Transactional
	public List<QuestionFile> updateQuestion(Question q, int[] delFileNo) {
		List<QuestionFile> delFileList = new ArrayList<QuestionFile>();
		int result = questionDao.updateQuestion(q);
		if(result>0) {
			for(QuestionFile questionFile : q.getFileList()) {
				result += questionDao.insertQuestionFile(questionFile);
			}
			if(delFileNo!=null) {
				for(int fileNo : delFileNo) {
					QuestionFile questionFile = questionDao.selectOneQuestionFile(fileNo);
					delFileList.add(questionFile);
					result += questionDao.deletequestionFile(fileNo);
				}
			}
		}
		int updateTotal = delFileNo==null?q.getFileList().size()+1:q.getFileList().size()+1+delFileNo.length;
		if(updateTotal == result) {
			return delFileList;
		}else {
			return null;
		}
	}

	public List<QuestionFile> deleteQuestion(int questionNo) {
		List<QuestionFile> delFileList = questionDao.selectAllFile(questionNo);
		if(!delFileList.isEmpty()) {
			int result = questionDao.deleteQuestion(questionNo);			
			return delFileList;
		}
		return null;
	}
}
