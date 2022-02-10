package com.kooli.app.kooli.controllers;


import javax.validation.Valid;

import com.kooli.app.kooli.payloads.requests.SubCategoryRequest;
import com.kooli.app.kooli.securities.CurrentUser;
import com.kooli.app.kooli.securities.UserPrincipal;
import com.kooli.app.kooli.services.SubCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(path = "/api/subcategory")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody SubCategoryRequest subCategoryRequest) {
        return ResponseEntity.ok(subCategoryService.save(subCategoryRequest));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> list(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.subCategoryService.list(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody  SubCategoryRequest subCategoryRequest, @PathVariable("id") long id) {
        return subCategoryService.update(id, subCategoryRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.subCategoryService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.subCategoryService.listById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> listByStatus(@CurrentUser UserPrincipal currentuser,
            @PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(this.subCategoryService.listByStatus(status));
    }
}

