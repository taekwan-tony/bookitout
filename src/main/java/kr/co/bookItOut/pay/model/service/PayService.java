package kr.co.bookItOut.pay.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookItOut.pay.model.dao.PayDao;

@Service
public class PayService {
	@Autowired
	private PayDao payDao;
}
