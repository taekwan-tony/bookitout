package kr.co.bookItOut.cart.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.cart.model.dao.CartDao;

@Service
public class CartService {
	@Autowired
	private CartDao cartDao;
	
	@Transactional
	public int insertCart(int bookNo, int memberNo) {
		int result = cartDao.insertCart(bookNo, memberNo);
		return result;
	}
	public int selectCart(int bookNo, int memberNo) {
		int result = cartDao.selectCartCount(bookNo, memberNo);
		if(result > 0) {
			result = cartDao.selectCart(bookNo, memberNo);
		}
		return result;
	}

	
}


