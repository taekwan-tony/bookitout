package kr.co.bookItOut.cart.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.cart.model.dao.CartDao;
import kr.co.bookItOut.cart.model.dto.Cart;
import kr.co.bookItOut.member.model.dto.Member;

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

	@Transactional
	public boolean success(String cartNoStr, int price, Member member) {
	    StringTokenizer sT = new StringTokenizer(cartNoStr, "/");
	    boolean result = true;

	    int payNo = cartDao.maxPayNo(); // 생성한 구매번호 가져옴
	    System.out.println("결제넘버 " + payNo);
	    while (sT.hasMoreTokens()) {
	        int cartNo = Integer.parseInt(sT.nextToken());
	        Cart cart = cartDao.selectCart(cartNo); // 카트 찾기
	        System.out.println("카트넘버 " + cartNo);
	        System.out.println("카트넘버로 찾은 카트 값" + cart);

	        int intResult2 = cartDao.success2(cartNo, member, cart, payNo); // 구매내역 디비 생성
	        int intResult3 = cartDao.success3(cartNo); // 카트디비 삭제

	        if (intResult2 == 0) {
	            result = false;
	            System.out.println("구매내역 디비 생성실패");
	            break;
	        }else if(intResult3 == 0){
	        	result = false;
	        	System.out.println("카트 삭제 실패");
	            break;
	        }
	    }

	    System.out.println(""+result);
	    return result;
	}
	
	@Transactional
	public boolean success1(int price, Member member, String addr,String name) {
	    boolean result = true;
	    int intResult1 = cartDao.success1(price, member, addr, name); // 구매 디비 생성
	    if (intResult1 == 0) { 
	        // 만약 구매 디비 생성에 실패하면 false를 반환
	        return false;
	    }
	    System.out.println("구매디비 생성 여부"+result);
	    return result;
	}
	
	@Transactional
	public List selPay(int memberNo, String name, String bookCount) {
		StringTokenizer sT1 = new StringTokenizer(name,"/");//name 몇갠지 / String값 구분
		StringTokenizer sT2 = new StringTokenizer(bookCount,"/");
		List<Cart> list = new ArrayList<Cart>();
		
		while(sT1.hasMoreTokens()) {//name 길이만큼 반복
			String bookName = sT1.nextToken();
			int bookCartCount = Integer.parseInt(sT2.nextToken());
			
			Book b = new Book();//Book 객체 생성
			b.setBookName(bookName);//new Book에 꺼내온 이름 넣음
			
			Cart c = cartDao.selPay(b, memberNo);
			int result = cartDao.setCount(c, memberNo, bookCartCount);
			c = cartDao.selPay(b, memberNo);
			//생성한 book객체(책이름), 멤버 이름, 책 이름 넣어서 멤버no에 연결된 카트 조회 >>
			//거기서 book객체에 있는 이름을 입력해서 거기에 해당하는 cartNo만 꺼내옴 (cart로 하는 이유는 수량까지 꺼내오기 위해)
			//즉 >> 체크된 book객체의 값만 꺼내올 수 있음
			//System.out.println("디비에서 꺼내온 카트객체(책 이름, 수량 , memberNo -- "+c);
			list.add(c);
		}
		
		return list;
	}
	

}


