package com.muratkhan.banking.services;

import com.muratkhan.banking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminService {
    Page<User> searchUsers(String name, String email, String phone, LocalDate birthDate, Pageable pageable);
}
