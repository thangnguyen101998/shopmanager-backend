package com.fa.training.shopmanager.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.model.BillDetails;
import com.fa.training.shopmanager.repository.BillDetailRepository;
import com.fa.training.shopmanager.service.BillDetailService;

@Service
@Transactional
public class BillDetailServiceImpl implements BillDetailService{
	
	@Autowired
	private BillDetailRepository billDetailRepository;

	@Override
	public BillDetails saveBillDetails(BillDetails billDetails) {
		return null;
	}

	@Override
	public List<BillDetails> findAllByBillDetailsByBillId(Long id) {
		return billDetailRepository.findAllByBillIdOrderByIdDesc(id);
	}

}
