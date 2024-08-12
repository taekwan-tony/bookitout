package kr.co.bookItOut.cart.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.bookItOut.book.model.dto.Book;
import kr.co.bookItOut.cart.model.dto.Cart;
import kr.co.bookItOut.cart.model.dto.CartRowMapper;
import kr.co.bookItOut.cart.model.dto.CartSelPayRowMapper;
import kr.co.bookItOut.cart.model.dto.CartSelRowMapper;
import kr.co.bookItOut.member.model.dto.Member;
import kr.co.bookItOut.pay.model.dto.Pay;
import kr.co.bookItOut.pay.model.dto.PayRowMapper2;
import kr.co.bookItOut.pay.model.dto.PayRowMapper3;

@Repository
public class CartDao {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private CartRowMapper cartRowMapper;

	@Autowired
	private CartSelRowMapper cartSelRowMapper;

	@Autowired
	private CartSelPayRowMapper cartSelPayRowMapper;
	
	@Autowired
	private PayRowMapper3 payRowMapper3;

	public int insertCart(int bookNo, int memberNo) {
		String query = "insert into cart_tbl values (CART_SEQ.nextval,?,1,?)";
		Object[] params = { bookNo, memberNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectCart(int bookNo, int memberNo) {
		String query = "select cart_no from cart_tbl where book_no=? and member_no=?";
		Object[] params = { bookNo, memberNo };
		int result = jdbc.queryForObject(query, Integer.class, params);
		return result;
	}

	public int selectCartCount(int bookNo, int memberNo) {
		String query = "select count(*) from cart_tbl where book_no=? and member_no=?";
		Object[] params = { bookNo, memberNo };
		int result = jdbc.queryForObject(query, Integer.class, params);
		return result;
	}

	public List selectAllCart(int memberNo) {
		String query = "SELECT cart_no, book_no, book_img, book_name, book_price, book_cart_count, member_no FROM cart_tbl JOIN book using (book_no) where member_no =?";
		Object[] params = { memberNo };
		List list = jdbc.query(query, cartSelRowMapper, params);
		return list;
	}

	public int selDel(Book b, int memberNo) {
		
		String query = "delete from cart_tbl WHERE book_no = ? AND member_no = ?";
		Object[] params = { b.getBookNo(), memberNo };
		int result = jdbc.update(query, params);

		return result;
	}

	public int plusCart(int cartNo) {
		String query = "update cart_tbl set book_cart_count = book_cart_count + 1  where cart_no=?";
		Object[] params = { cartNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public Cart selPay(Book b, int memberNo) {
		String query = "select cart_no, book_img, book_no, book_cart_count, member_no, book_name, book_price from cart_tbl join book using (book_no) where book_no=? and member_no=?";
		Object[] params = {b.getBookNo(), memberNo};
		System.out.println("selPay로 넘어갈 북 넘버 제대로 조회 되는지 "+b.getBookNo() + memberNo);
		List<Cart> c = jdbc.query(query, cartSelPayRowMapper, params);

		if (c != null && !c.isEmpty()) {
			System.out.println("selPay로 넘어갈 cart객체"+c);
			return c.get(0);
		} else {
			Cart cc = new Cart();
			return cc;
		}

	}

	public int success1(int price, Member member, String addr,String name) {//구매 디비 생성(구매번호(seq), 총 금액, 구매날짜, 회원번호)
		String query = "insert into pay values (PAY_SEQ.NEXTVAL,'KG',?,to_char(sysdate,'yyyy-mm-dd'),?,?,?)";
		Object[] params = {member.getMemberNo(), price, addr, name};
		int result = jdbc.update(query, params);
		return result;
		
	}

	public int success2(int cartNo, Member member, Cart cart, int payNo) {//구매내역 디비 생성(구매내역번호(seq), 책번호, 구매번호, 구매수량, 회원번호)
		String query = "INSERT INTO pay_menu VALUES (pay_menu_seq.NEXTVAL, ?, ?,?,?)";
		Object[] params = {cart.getBookNo(), payNo, cart.getBookCartCount(), member.getMemberNo()};
		int result = jdbc.update(query, params);
		return result;
	}


	public int success3(int cartNo) {
		String query = "delete from cart_tbl where cart_no=?";
		Object[] params= {cartNo};
		int result = jdbc.update(query,params);
		
		return result;
	}

	public Cart selectCart(int cartNo) {
		String query = "SELECT cart_no, book_no, book_img, book_name, book_price, book_cart_count, member_no FROM cart_tbl JOIN book using (book_no) where cart_no =?";
		Object[] params = {cartNo};
		List<Cart> c = jdbc.query(query, cartSelRowMapper, params);
		return (Cart)c.get(0);
	}

	public int maxPayNo() {
		String query = "select max(pay_no) from pay";
		Integer maxPayNo = jdbc.queryForObject(query, Integer.class);
	    return maxPayNo;
	}

	public int setCount(Cart c, int memberNo, int bookCartCount) {
		String query = "update cart_tbl set book_cart_count = ?  where cart_no=?";
		Object[] params = {bookCartCount, c.getCartNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectPayNames(int payNo) {
		String query = "SELECT b.book_name FROM pay_menu pm JOIN book b ON pm.book_no = b.book_no WHERE pm.pay_no = ?";
		Object[] params = {payNo};
		List list = jdbc.query(query, payRowMapper3, params);
		return list;
	}

	public int selectCartNo() {//
		String query = "select max(cart_no) from cart_tbl";
		int cartNo = jdbc.queryForObject(query, Integer.class);
		return cartNo;
	}

}
