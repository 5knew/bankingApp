package com.muratkhan.banking.controller;
import com.muratkhan.banking.dto.UserContactUpdateRequest;
import com.muratkhan.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi User");
    }
    @PreAuthorize("#userId == authentication.principal.id")
    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(@PathVariable Long userId, @RequestBody String email) {
        try {
            userService.updateUserEmail(userId, email);
            return ResponseEntity.ok().body("User email updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("#userId == authentication.principal.id")
    @PatchMapping("/phone")
    public ResponseEntity<?> updatePhone(@PathVariable Long userId, @RequestBody String phone) {
        try {
            userService.updateUserPhone(userId, phone);
            return ResponseEntity.ok().body("User phone updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}


