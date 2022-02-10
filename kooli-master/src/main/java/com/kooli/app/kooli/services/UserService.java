package com.kooli.app.kooli.services;

import java.util.List;
import java.util.Optional; 

import javax.validation.Valid;

import com.kooli.app.kooli.models.Category;
import com.kooli.app.kooli.models.RecordStatus;
import com.kooli.app.kooli.models.Role;
import com.kooli.app.kooli.models.SubCategory;
import com.kooli.app.kooli.models.User;
import com.kooli.app.kooli.models.WideRecordStatus;
import com.kooli.app.kooli.payloads.requests.UserRequest;
import com.kooli.app.kooli.payloads.requests.UserUpdateRequest;
import com.kooli.app.kooli.payloads.response.ApiResponse;
import com.kooli.app.kooli.repositories.CategoryRepository;
import com.kooli.app.kooli.repositories.SubCategoryRepository;
import com.kooli.app.kooli.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService<RoleName, RoleRepository> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	RoleRepository roleRepository;
    
	@Transactional
	public User save(User user) {
		user = this.userRepository.save(user);
		return user;
	}

	public boolean hasRole(RoleName role) {
		return SecurityContextHolder.getContext().getAuthentication( ).getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role.toString()));
	}

	public boolean hasRole(User user, RoleName role) {
		return user.getRoles().stream().anyMatch(eachRole -> ((Role) eachRole).getName().equals(role));
	}

	@Transactional
	public ResponseEntity<?> save(@Valid UserRequest userRequest) {
		try {
			User user = new User();
			Optional<Category> category = categoryRepository.findById(Long.parseLong(userRequest.getCategory()));

			if (!category.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			Optional<SubCategory> subCategory = subCategoryRepository
					.findById(Long.parseLong(userRequest.getSubcategory()));

			if (!subCategory.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			user.setId((long) 0);
			user.setUsername(userRequest.getUsername());
			user.setMobile(userRequest.getMobile());
			user.setEmail(userRequest.getEmail());
			user.setPassword(userRequest.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setType(userRequest.getType());
			user.setFirstname(userRequest.getFirstname());
			user.setLastname(userRequest.getLastname());
			user.setTitle(userRequest.getTitle());
			user.setImage(userRequest.getImage());
			user.setDescription(userRequest.getDescription());
			user.setCompanyname(userRequest.getCompanyname());
			user.setGender(userRequest.getGender());
			user.setDob(userRequest.getDob());
			user.setStatus(WideRecordStatus.ACTIVE);
			user.setCategory(category.get());
			user.setSubCategory(subCategory.get());

			user = this.userRepository.save(user);

			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", user), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.toString());
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	// @Transactional
	// public ResponseEntity<?> save(@Valid UserRequest userRequest) {
	// try {
	// User user = new User();
	// Optional<Address> address = addressRepository
	// .findById(Long.parseLong(userRequest.getAddress()));

	// if (!address.isPresent())
	// return new ResponseEntity<>(new ApiResponse(false, "Item not found"),
	// HttpStatus.BAD_REQUEST);

	// Optional<Category> category =
	// categoryRepository.findById(Long.parseLong(userRequest.getCategory()));

	// if (!category.isPresent())
	// return new ResponseEntity<>(new ApiResponse(false, "Item not found"),
	// HttpStatus.BAD_REQUEST);

	// Optional<SubCategory> subCategory = subCategoryRepository
	// .findById(Long.parseLong(userRequest.getSubcategory()));

	// if (!subCategory.isPresent())
	// return new ResponseEntity<>(new ApiResponse(false, "Item not found"),
	// HttpStatus.BAD_REQUEST);

	// user.setId((long) 0);
	// user.setUsername(userRequest.getUsername());
	// user.setMobile(userRequest.getMobile());
	// user.setEmail(userRequest.getEmail());
	// user.setPassword(userRequest.getPassword());
	// user.setType(userRequest.getType());
	// user.setFirstname(userRequest.getFirstname());
	// user.setLastname(userRequest.getLastname());
	// user.setTitle(userRequest.getTitle());
	// user.setImage(userRequest.getImage());
	// user.setDescription(userRequest.getDescription());
	// user.setCompanyname(userRequest.getCompanyname());
	// user.setGender(userRequest.getGender());
	// user.setDob(userRequest.getDob());
	// user.setDob(userRequest.getDob());
	// user.setStatus(WideRecordStatus.ACTIVE);
	// user.setAddress(address.get());
	// user.setCategory(category.get());
	// user.setSubcategory(subCategory.get());

	// user = this.userRepository.save(user);

	// return new ResponseEntity<>(new ApiResponse(true, "Saved successfully",
	// user), HttpStatus.OK);
	// } catch (Exception e) {
	// System.out.println( e);
	// return new ResponseEntity<>(new ApiResponse(false, e.toString()),
	// HttpStatus.BAD_REQUEST);
	// }

	@Transactional
	public ResponseEntity<?> list() {
		try {
			List<User> list = this.userRepository.findAll();
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByType(String type) {
		return new ResponseEntity<>(
				new ApiResponse(true, "", this.userRepository.findByType(type)),
				HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<?> listById(long id) {
		try {
			Optional<User> user = this.userRepository.findById(id);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByUsername(String username) {
		try {
			Optional<User> user = this.userRepository.findByUsername(username);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByMobile(String mobile) {
		try {
			Optional<User> user = this.userRepository.findByMobile(mobile);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByEmail(String email) {
		try {
			Optional<User> user = this.userRepository.findByEmail(email);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByCategoryAndStatus(long id, String status) {
		try {
			RecordStatus recordStatus = RecordStatus.ACTIVE;

			switch (status) {
				case "INACTIVE":
					recordStatus = RecordStatus.INACTIVE;
					break;
				case "DELETED":
					recordStatus = RecordStatus.DELETED;
					break;
				default:
					recordStatus = RecordStatus.ACTIVE;
					break;
			}

			List<User> list = this.userRepository.findByCategoryAndStatus(id, recordStatus);
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> delete(long id) {
		try {
			this.userRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> update(long id, @Valid UserUpdateRequest userRequest) {
		try {
			Optional<User> user = this.userRepository.findById(id);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			Optional<Category> category = categoryRepository.findById(Long.parseLong(userRequest.getCategory()));

			if (!category.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			Optional<SubCategory> subCategory = subCategoryRepository
					.findById(Long.parseLong(userRequest.getSubcategory()));

			if (!subCategory.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			user.get().setMobile(userRequest.getMobile());
			user.get().setEmail(userRequest.getEmail());
			user.get().setFirstname(userRequest.getFirstname());
			user.get().setLastname(userRequest.getLastname());
			user.get().setTitle(userRequest.getTitle());

			user.get().setDescription(userRequest.getDescription());
			user.get().setCompanyname(userRequest.getCompanyname());
			user.get().setGender(userRequest.getGender());
			user.get().setDob(userRequest.getDob());
			user.get().setStatus(WideRecordStatus.ACTIVE);
			user.get().setCategory(category.get());
			user.get().setSubCategory(subCategory.get());

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.userRepository.save(user.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> updateImage(long uid, String image) {
		try {
			Optional<User> user = this.userRepository.findById(uid);

			if (!user.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			user.get().setImage(image);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.userRepository.save(user.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}
}