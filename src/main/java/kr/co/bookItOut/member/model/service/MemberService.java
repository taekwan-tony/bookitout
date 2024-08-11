package kr.co.bookItOut.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.member.model.dao.MemberDao;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.pay.model.dto.Pay;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;

	public Member selectOneMember(String memberId, String memberPw) {
		Member member = memberDao.selectOneMember(memberId, memberPw);
		return member;
	}

	public Member selectOneMember(String checkId) {
		Member member = memberDao.selectOneMember(checkId);
		return member;
	}

	public int insertMember(Member m) {
		int result = memberDao.insertMember(m);
		return result;
	}

	public int updateMember(Member member, Member m, int sel) {
		int result = memberDao.updateMember(member, m, sel);
		return result;
	}

	public Member selectSearchId(Member m) {
		Member member = memberDao.selectSearchId(m);
		return member;
	}

	public Member selectSearchPw(Member m) {
		Member member = memberDao.selectSearchPw(m);
		return member;
	}

	public List selectAllPay(int memberNo) {
		List list = memberDao.selectAllCart(memberNo);
		return list;
	}

	public List selectMyBoard(String memberId) {
		List list = memberDao.selectMyBoard(memberId);
		return list;
	}
	
}
