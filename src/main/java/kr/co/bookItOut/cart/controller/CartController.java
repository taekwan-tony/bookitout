package kr.co.bookItOut.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.bookItOut.cart.model.service.CartService;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
	@Autowired
	private CartService cartService;

}
