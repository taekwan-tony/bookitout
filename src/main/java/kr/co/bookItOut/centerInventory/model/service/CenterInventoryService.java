package kr.co.bookItOut.centerInventory.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.centerInventory.model.dao.CenterInventoryDao;

@Service
public class CenterInventoryService {
	@Autowired
	private CenterInventoryDao centerInventoryDao;

	public List selectAllCenterInventory() {
		List list = centerInventoryDao.selectAllCenterInventory();
		return list;
	}
}
