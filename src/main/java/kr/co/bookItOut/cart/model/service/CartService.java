package kr.co.bookItOut.cart.model.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.book.model.dto.Book;
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

	


	public List selectAllCart(int memberNo) {
		List list = cartDao.selectAllCart(memberNo);
		return list;
	}

	@Transactional
	public boolean selDel(String name, int memberNo) {
		
		StringTokenizer sT = new StringTokenizer(name,"/");
		boolean result = true;
		while(sT.hasMoreTokens()) {
			String bookName = sT.nextToken();
			Book b = new Book();
			b.setBookName(bookName);
			int intResult = cartDao.selDel(b, memberNo);
			if(intResult == 0) {
				result = false;
				break;
			}
		}
		
		return result;
	}

}


