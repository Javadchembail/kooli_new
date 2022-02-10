package com.kooli.app.kooli.services;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.kooli.app.kooli.models.RecordStatus;
import com.kooli.app.kooli.models.SubCategory;
import com.kooli.app.kooli.payloads.requests.SubCategoryRequest;
import com.kooli.app.kooli.payloads.response.ApiResponse;
import com.kooli.app.kooli.repositories.SubCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Transactional
    public ResponseEntity<?> save(@Valid SubCategoryRequest subCategoryRequest) {
        try {
            SubCategory subCategory = new SubCategory();
            subCategory.setId((long) 0);
            subCategory.setName(subCategoryRequest.getName());
            subCategory.setStatus(RecordStatus.ACTIVE);
            subCategory = this.subCategoryRepository.save(subCategory);
            return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", subCategory), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }

    }
    @Transactional
    public ResponseEntity<?> list(long id) {
        try {
            List<SubCategory> list = this.subCategoryRepository.findByCategoryId(id);
            return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<?> listById(long id) {
        try {
            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(id);

            if (!subCategory.isPresent())
                return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(new ApiResponse(true, "", subCategory), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<?> listByStatus( String status) {
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

            List<SubCategory> list = this.subCategoryRepository.findByStatus(recordStatus);
            return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> delete(long id) {
        try {
            this.subCategoryRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }

    }



    public ResponseEntity<?> update(long id, @Valid SubCategoryRequest subCategoryRequest) {
        try {
            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(id);

            if (!subCategory.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

                subCategory.get().setId((long) 0);
                subCategory.get().setName(subCategoryRequest.getName());
                subCategory.get().setStatus(RecordStatus.ACTIVE);
            return new ResponseEntity<>(
                    new ApiResponse(true, "Updated successfully", this.subCategoryRepository.save(subCategory.get())),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }

    }
    public Object listByCategoryAndStatus(long id, String status) {
        return null;
    }
    public Object list() {
        return null;
    }
}
