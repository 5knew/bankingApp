package com.muratkhan.banking.controller;
import com.muratkhan.banking.dto.UserContactUpdateRequest;
import com.muratkhan.banking.model.User;
import com.muratkhan.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok("Hi User: " + currentUser.getUsername());
    }
    @PreAuthorize("#userId == authentication.principal.id")
    @PatchMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody String email) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long currentUserId = currentUser.getId();
            userService.updateUserEmail(currentUserId, email);
            return ResponseEntity.ok().body("User email updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("#userId == authentication.principal.id")
    @PatchMapping("/updatePhone")
    public ResponseEntity<?> updatePhone(@RequestBody String phone) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long currentUserId = currentUser.getId();
            userService.updateUserPhone(currentUserId, phone);
            return ResponseEntity.ok().body("User phone updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/{userId}/contact-info")
    public ResponseEntity<?> updateContactInfo(@RequestBody UserContactUpdateRequest request) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long currentUserId = currentUser.getId();
            userService.updateContactInfo(currentUserId, request.getEmail(), request.getPhone());
            return ResponseEntity.ok().body("Contact information updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}


