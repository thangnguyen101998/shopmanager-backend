package com.fa.training.shopmanager.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.model.Users;
import com.fa.training.shopmanager.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserAPIController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{currentpage}/{pagesize}")
	public ResponseEntity<?> findAllUser(@PathVariable("currentpage")int currentPage,
			@PathVariable("pagesize") int pageSize){
		Page<Users> users = userService.findAllByDeletedIsFalse(PageRequest.of(currentPage,pageSize));
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> saveUser(@RequestBody Users users){
		Users newUser = userService.saveUser(users);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	@GetMapping("/all-list")
	public ResponseEntity<?> findAllList(){
		List<Users> users = userService.findAllByDeletedIsFalseOrderByIdAsc();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
