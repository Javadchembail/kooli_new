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
@RequestMapping(path = "/api/shop")
public class CategoryController {

    @Autowired
    private SubCategoryService shopCategoryService;

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody SubCategoryRequest shopCategoryRequest) {
        return ResponseEntity.ok(shopCategoryService.save(shopCategoryRequest));
    }

    @GetMapping
    public ResponseEntity<?> list(@CurrentUser UserPrincipal currentuser) {
        return ResponseEntity.ok(this.shopCategoryService.list());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody  SubCategoryRequest shopCategoryRequest, @PathVariable("id") long id) {
        return shopCategoryService.update(id, shopCategoryRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.shopCategoryService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.shopCategoryService.listById(id));
    }

    @GetMapping("/category/{id}/status/{status}")
    public ResponseEntity<?> listByCategoryAndStatus(@CurrentUser UserPrincipal currentuser,
            @PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(this.shopCategoryService.listByCategoryAndStatus(id, status));
    }
}
