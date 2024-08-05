package kr.co.bookItOut.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.member.model.dao.MemberDao;
import kr.co.bookItOut.member.model.dto.Member;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;

	public Member selectOneMember(String memberId, String memberPw) {
		Member member = memberDao.selectOneMember(memberId, memberPw);
		return member;
	}
	
}
