package com.kooli.app.kooli.controllers;



import javax.validation.Valid;

import com.kooli.app.kooli.payloads.requests.UserRequest;
import com.kooli.app.kooli.payloads.requests.UserUpdateRequest;
import com.kooli.app.kooli.securities.CurrentUser;
import com.kooli.app.kooli.securities.UserPrincipal;
import com.kooli.app.kooli.services.UserService;

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
@RequestMapping(path = "/api/user")
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.save(userRequest));
    }

    @GetMapping
    public ResponseEntity<?> list(@CurrentUser UserPrincipal currentuser) {
        return ResponseEntity.ok(this.userService.list());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> listByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(this.userService.listByType(type));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal currentuser,
            @Valid @RequestBody UserUpdateRequest userRequest, @PathVariable("id") long id) {
        return userService.update(id, userRequest);
    }

    @PatchMapping("/image/{image}")
    public ResponseEntity<?> updateImage(@CurrentUser UserPrincipal currentuser,
            @PathVariable("image") String image) {
        return userService.updateImage(currentuser.getId(), image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return this.userService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@CurrentUser UserPrincipal currentuser, @PathVariable("id") long id) {
        return ResponseEntity.ok(this.userService.listById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> listByUsername(@CurrentUser UserPrincipal currentuser,
            @PathVariable("username") String username) {
        return ResponseEntity.ok(this.userService.listByUsername(username));
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<?> listByMobile(@CurrentUser UserPrincipal currentuser,
            @PathVariable("mobile") String mobile) {
        return ResponseEntity.ok(this.userService.listByMobile(mobile));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> listByEmail(@CurrentUser UserPrincipal currentuser,
            @PathVariable("email") String email) {
        return ResponseEntity.ok(this.userService.listByEmail(email));
    }

    @GetMapping("/category/{id}/status/{status}")
    public ResponseEntity<?> listByCategoryAndStatus(@CurrentUser UserPrincipal currentuser,
            @PathVariable("id") long id, @PathVariable("status") String status) {
        return ResponseEntity.ok(this.userService.listByCategoryAndStatus(id, status));
    }
}
