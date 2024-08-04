package kr.co.bookItOut.admin.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.admin.model.dao.AdminDao;
import kr.co.bookItOut.admin.model.dto.AdminListData;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public AdminListData selectAdminList(int rePage) {
		
		return null;
	}
}
