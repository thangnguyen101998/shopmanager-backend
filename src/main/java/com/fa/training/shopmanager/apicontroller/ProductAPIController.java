package com.fa.training.shopmanager.apicontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fa.training.shopmanager.customexception.ProductNotFoundException;
import com.fa.training.shopmanager.model.Product;
import com.fa.training.shopmanager.service.ProductService;
import com.fa.training.shopmanager.validator.ValidatorProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {
	
	@Autowired
	private ProductService productService;
	
	Map<String, String> errors;
	
	@GetMapping("/{currentpage}/{pagesize}")
	public ResponseEntity<?> getProducts(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<Product> products = productService.findAllByDeletedIsFalse(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping("/{name}/{currentpage}/{pagesize}")
	public ResponseEntity<?> searchProductByName(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage, @PathVariable("name") String name){
		Page<Product> products = productService.findByNameContaining(name, PageRequest.of(currentPage,pageSize));
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-name-desc/{currentpage}/{pagesize}")
	public ResponseEntity<?> showAllProductOrderByNameDesc(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<Product> products = productService.findAllByDeletedIsFalseOrderByNameDesc(PageRequest.of(currentPage,pageSize));
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping("/sort-by-name-asc/{currentpage}/{pagesize}")
	public ResponseEntity<?> showAllProductOrderByNameAsc(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<Product> products = productService.findAllByDeletedIsFalseOrderByNameAsc(PageRequest.of(currentPage,pageSize));
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping("/all-list")
	public ResponseEntity<?> findAllProducts() {
		List<Product> products = productService.findAllProduct();
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> saveProduct( @RequestParam("product")String productString, @RequestParam("file")MultipartFile image,HttpServletRequest request) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(productString, Product.class);
		errors = ValidatorProduct.validatorProduct(product);
		if(errors.size() > 0) {
			return new ResponseEntity<>(errors,HttpStatus.NOT_ACCEPTABLE);
		}
		Product newProduct = productService.saveProduct( product,request,image);
		return new ResponseEntity<>(newProduct, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id")Long id){
		Product product = productService.findProductByIdAndDeletedIsFalse(id);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
	
	@GetMapping("/all-by-product-category-id/{id}/{currentpage}/{pagesize}")
	public ResponseEntity<?> getAllByProductCategoryId(@PathVariable("id") Long id, 
			@PathVariable("currentpage")int currentPage, @PathVariable("pagesize")int pageSize){
		Page<Product> products = productService.findAllByProductCateogryId(id, PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@PatchMapping("/edit")
	public ResponseEntity<?>editProductById(@RequestParam("product") String productJson, @RequestParam("file") MultipartFile image,
			HttpServletRequest request) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(productJson, Product.class);
		errors = ValidatorProduct.validatorProduct(product);
		if(errors.size() > 0) {
			return new ResponseEntity<>(errors,HttpStatus.NOT_ACCEPTABLE);
		}
		productService.updateProduct(product,request,image);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-multiple/{ids}")
	public ResponseEntity<?> deleteProducts(@PathVariable("ids") List<Long> ids){
		productService.deleteProducts(ids);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/find-by-name/{name}")
	public ResponseEntity<?> findProductByName(@PathVariable("name") String name){
		Optional<Product> productOp = productService.findProductByNameAndDeletedIsFalse(name);
		if(productOp.isEmpty()) {
			throw new ProductNotFoundException("* sản phẩm không tồn tại");
		}
		return new ResponseEntity<>(productOp.get(), HttpStatus.OK);
	}
	
	@GetMapping("/image-product/{id}")
	public byte[] getImage(@PathVariable("id")Long id) throws IOException {
		Product product = productService.findProductByIdAndDeletedIsFalse(id);
		Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/upload/" +product.getImageUrl());
		return Files.readAllBytes(path);
	}
}
