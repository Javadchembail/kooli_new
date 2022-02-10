package com.kooli.app.kooli.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.kooli.app.kooli.models.Category;
import com.kooli.app.kooli.models.RecordStatus;
import com.kooli.app.kooli.payloads.requests.CategoryRequest;
import com.kooli.app.kooli.payloads.response.ApiResponse;
import com.kooli.app.kooli.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service   
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional
	public ResponseEntity<?> save(@Valid CategoryRequest categoryRequest) {
		try {
			Category category = new Category();

			category.setId((long) 0);
			category.setName(categoryRequest.getName());

			category.setStatus(RecordStatus.ACTIVE);

			category = this.categoryRepository.save(category);

			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", category), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public ResponseEntity<?> list() {
		try {
			List<Category> list = this.categoryRepository.findAll();
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listById(long id) {
		try {
			Optional<Category> category = this.categoryRepository.findById(id);

			if (!category.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", category), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByStatus(String status) {
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

			List<Category> list = this.categoryRepository.findByStatus(recordStatus);
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> update(long id, @Valid CategoryRequest categoryRequest) {
		try {
			Optional<Category> category = this.categoryRepository.findById(id);

			if (!category.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			category.get().setName(categoryRequest.getName());

			category.get().setStatus(RecordStatus.ACTIVE);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.categoryRepository.save(category.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> delete(long id) {
		try {
			this.categoryRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public ResponseEntity<?> listByUserId(long id) {
		// List<Product> products =
		// this.categoryRepository.findDistinctCategoryByUserIdAndStatus(id,
		// RecordStatus.ACTIVE);
		// System.out.println(products.size());

		List<Category> categories = this.categoryRepository.getDistinctCategoryByUserIdAndStatus(id,
				RecordStatus.ACTIVE);
		// List<Category> categories = new ArrayList<Category>();
		// for (Product item : products) {
		// categories.add(item.getCategory());
		// }

		return new ResponseEntity<>(new ApiResponse(true, "", categories), HttpStatus.OK);
	}

}
