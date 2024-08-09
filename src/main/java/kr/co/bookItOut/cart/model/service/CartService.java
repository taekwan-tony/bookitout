package kr.co.bookItOut.cart.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.cart.model.dao.CartDao;
import kr.co.bookItOut.cart.model.dto.Cart;

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
	@Transactional
	public int plusCart( int cartNo) {
		int result = cartDao.plusCart(cartNo);
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

	public boolean success(String cartNoStr) {
		StringTokenizer sT = new StringTokenizer(cartNoStr,"/");
		boolean result = true;
		while(sT.hasMoreTokens()) {
			int cartNo = Integer.parseInt(sT.nextToken());
			int intResult = cartDao.success(cartNo);
			if(intResult == 0) {
				result = false;
				break;
			}
		}
		return false;
	}
	
	@Transactional
	public List selPay(int memberNo, String name) {
		StringTokenizer sT = new StringTokenizer(name,"/");//name 몇갠지 / String값 구분
		List<Cart> list = new ArrayList<Cart>();
		
		while(sT.hasMoreTokens()) {//name 길이만큼 반복
			String bookName = sT.nextToken();
			Book b = new Book();//Book 객체 생성
			b.setBookName(bookName);//new Book에 꺼내온 이름 넣음
			
			Cart c = cartDao.selPay(b, memberNo);
			//생성한 book객체(책이름), 멤버 이름, 책 이름 넣어서 멤버no에 연결된 카트 조회 >>
			//거기서 book객체에 있는 이름을 입력해서 거기에 해당하는 cartNo만 꺼내옴 (cart로 하는 이유는 수량까지 꺼내오기 위해)
			//즉 >> 체크된 book객체의 값만 꺼내올 수 있음
			//System.out.println("디비에서 꺼내온 카트객체(책 이름, 수량 , memberNo -- "+c);
			list.add(c);
		}
		
		return list;
	}
	

}


