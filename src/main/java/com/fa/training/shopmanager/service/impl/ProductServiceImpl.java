package com.fa.training.shopmanager.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fa.training.shopmanager.customexception.ProductConflictException;
import com.fa.training.shopmanager.customexception.ProductNotFoundException;
import com.fa.training.shopmanager.model.Product;
import com.fa.training.shopmanager.repository.ProductRepository;
import com.fa.training.shopmanager.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Page<Product> findAllByDeletedIsFalse(Pageable pageable) {
		return productRepository.findAllByDeletedIsFalseOrderByIdDesc(pageable);
	}

	@Override
	public Page<Product> findByNameContaining(String name, Pageable pageable) {
		return productRepository.findByNameContainingIgnoreCaseAndDeletedIsFalseOrderByNameDesc(name, pageable);
	}

	@Override
	public Page<Product> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable) {
		return productRepository.findAllByDeletedIsFalseOrderByNameDesc(pageable);
	}
	
	@Override
	public Page<Product> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable) {
		return productRepository.findAllByDeletedIsFalseOrderByNameAsc(pageable);
	}

	@Override
	public Product saveProduct(Product product,HttpServletRequest request,MultipartFile image) {
		Optional<Product> productOp = findProductByNameAndDeletedIsFalse(product.getName());
		if(!productOp.isEmpty()) {
			throw new ProductConflictException("* sản phẩm đã tồn tại");
		}
		try {
			String uploadRoot = this.saveUploadedFiles(request,image, product);
		} catch (IOException e) {
			e.printStackTrace();
		}
		product.setCreatedDate(LocalDate.now());
		return productRepository.save(product);
	}

	@Override
	public Product findProductByIdAndDeletedIsFalse(Long id) {
		return productRepository.findProductByIdAndDeletedIsFalse(id)
				.orElseThrow(() -> new ProductNotFoundException("* không tìm thấy sản phẩm "));
	}

	@Override
	public Optional<Product> findProductByNameAndDeletedIsFalse(String name) {
		return productRepository.findProductByNameAndDeletedIsFalse(name);
	}

	@Override
	public void updateProduct(Product product,HttpServletRequest request,MultipartFile image) {
		Optional<Product> productOp = findProductByNameAndDeletedIsFalse(product.getName());
		if(!productOp.isEmpty() && productOp.get().getId() != product.getId()) {
			throw new ProductConflictException("* sản phẩm đã tồn tại");
		}
		try {
			System.out.println(image.getOriginalFilename());
			if(!image.getOriginalFilename().equals("blob")) {
			String uploadRoot = this.saveUploadedFiles(request,image, product);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		product.setModifiedDate(LocalDate.now());
		productRepository.updateProduct(product.getName(), product.getPrice(), product.getImageUrl(),
				product.getDescription(), product.getProductCategoryId(), product.getModifiedDate(),
				product.getId());
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepository.deleteProduct(productId);
	}

	@Override
	public Page<Product> findAllByProductCateogryId(Long productCategoryId, Pageable pageable) {
		return productRepository.findAllByDeletedIsFalseAndProductCategoryIdOrderByIdDesc(productCategoryId, pageable);
	}

	@Override
	public List<Product> findAllProduct() {
		return productRepository.findAllByDeletedIsFalseOrderByIdDesc();
	}
	
	private String saveUploadedFiles(HttpServletRequest request, MultipartFile files, Product product) throws IOException {
        String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload";
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        
        System.out.println("uploadRootPath=" + uploadRootPath);
        File uploadRootDir = new File(uploadRootPath);
        File uploadDir = new File(UPLOAD_DIR);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

            // Tên file gốc tại Client.
            String name = files.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                try {
                    // Tạo file tại Server.
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    // Luồng ghi dữ liệu vào file trên Server.
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(files.getBytes());
                    stream.close();

                    File localFile = new File(uploadDir.getAbsolutePath() + File.separator + name);
                    BufferedOutputStream streamLocal = new BufferedOutputStream(new FileOutputStream(localFile));
                    streamLocal.write(files.getBytes());
                    streamLocal.close();
                    product.setImageUrl(name);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        return uploadRootDir.toString();
    }

	@Override
	public void updateQuantityProduct(int quantity, Long productId) {
		productRepository.updateQuantityProduct(quantity, productId);
	}

	@Override
	public void deleteProducts(List<Long> ids) {
		for(Long id: ids) {
			productRepository.deleteProduct(id);
		}
	}
	
}
