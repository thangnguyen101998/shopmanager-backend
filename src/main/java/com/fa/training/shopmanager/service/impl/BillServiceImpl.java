package com.fa.training.shopmanager.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.customexception.BillNotFoundException;
import com.fa.training.shopmanager.dto.BillDTO;
import com.fa.training.shopmanager.dto.BillDetailDTO;
import com.fa.training.shopmanager.model.Bill;
import com.fa.training.shopmanager.model.BillDetails;
import com.fa.training.shopmanager.model.Product;
import com.fa.training.shopmanager.repository.BillDetailRepository;
import com.fa.training.shopmanager.repository.BillRepository;
import com.fa.training.shopmanager.service.BillService;
import com.fa.training.shopmanager.service.ProductService;

@Service
@Transactional
public class BillServiceImpl implements BillService {
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private BillDetailRepository billDetailRepository;
	
	@Autowired
	private ProductService productService;

	@Override
	public Bill saveBill(BillDTO billDTO) {
		Bill bill = new Bill();
		bill.setCreatedDate(convertToLocalDate(billDTO.getCreatedDate()));
		bill.setUserId(Long.parseLong(billDTO.getUserId()));
		double total = 0;
		for(BillDetailDTO billDetailDTO : billDTO.getBillDetails()) {
			total += billDetailDTO.getQuantity() * billDetailDTO.getPrice();
		}
		bill.setTotal(total);
		
		Bill newBill = billRepository.save(bill);
		
		for(BillDetailDTO billDetailDTO : billDTO.getBillDetails()) {
			Optional<Product> product = productService.findProductByNameAndDeletedIsFalse(billDetailDTO.getProduct().getName());
			int quantity = product.get().getQuantity() - billDetailDTO.getQuantity();
			productService.updateQuantityProduct(quantity,product.get().getId());
			BillDetails billDetails = new BillDetails();
			billDetails.setQuantity(billDetailDTO.getQuantity());
			billDetails.setPrice(billDetailDTO.getPrice());
			billDetails.setProductId(product.get().getId());
			billDetails.setBillId(newBill.getId());
			
			billDetailRepository.save(billDetails);
		}
		return newBill;
	}
	
	public LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

	@Override
	public Page<Bill> findByDeletedIsFalseOrderByIdDesc(Pageable pageable) {
		return billRepository.findByDeletedIsFalseOrderByIdDesc(pageable);
	}

	@Override
	public Bill findById(Long id) {
		return billRepository.findByIdAndDeletedIsFalse(id)
				.orElseThrow(() -> new BillNotFoundException("* hóa đơn không tồn tại"));
	}

	@Override
	public void deleteBillById(Long id) {
		billRepository.deleteBill(id);
		List<BillDetails> billDetails = billDetailRepository.findAllByBillIdOrderByIdDesc(id);
		billDetailRepository.deleteBillDetailByBillId(id);
		for(BillDetails billDetail: billDetails) {
			Product productOp = productService.findProductByIdAndDeletedIsFalse(billDetail.getProductId());
			int quantity = productOp.getQuantity() + billDetail.getQuantity();
			productService.updateQuantityProduct(quantity, productOp.getId());
		}
	}

	
}
