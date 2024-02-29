package com.muratkhan.banking.controller;

import com.muratkhan.banking.model.User;
import com.muratkhan.banking.services.AdminService;
import com.muratkhan.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi Admin");
    }

    @GetMapping("/search")
    public Page<User> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            Pageable pageable) {
        return adminService.searchUsers(name, email, phone, birthDate, pageable);
    }
}
