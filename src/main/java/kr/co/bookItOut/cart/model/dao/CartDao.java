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
		String query = "delete from cart_tbl WHERE book_no = (SELECT book_no  FROM book WHERE book_name = ?) AND member_no = ?";
		Object[] params = { b.getBookName(), memberNo };
		int result = jdbc.update(query, params);

		return result;
	}

	public int success(int cartNo) {
		String query = "delete from cart_tbl where cart_no=?";
		Object[] params= {cartNo};
		int result = jdbc.update(query,params);
		
		return result;
	}

	public int plusCart(int cartNo) {
		String query = "update cart_tbl set book_cart_count = book_cart_count + 1  where cart_no=?";
		Object[] params = { cartNo };
		int result = jdbc.update(query, params);
		return result;
	}

	public Cart selPay(Book b, int memberNo) {
		String query = "select cart_no, book_no, book_cart_count, member_no, book_name, book_price from cart_tbl join book using (book_no) where book_name=? and member_no=?";
		Object[] params = { b.getBookName(), memberNo };
		System.out.println(b.getBookName() + memberNo);
		List<Cart> c = jdbc.query(query, cartSelPayRowMapper, params);

		if (c != null && !c.isEmpty()) {
			return c.get(0);
		} else {
			Cart cc = new Cart();
			return cc;
		}

	}

}
