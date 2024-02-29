package com.muratkhan.banking.services.impl;

import com.muratkhan.banking.model.User;
import com.muratkhan.banking.repositories.AdminRepository;
import com.muratkhan.banking.repositories.UserRepository;
import com.muratkhan.banking.services.AdminService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    @Override
    public Page<User> searchUsers(String name, String email, String phone, LocalDate birthDate, Pageable pageable) {
        return adminRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), name + "%"));
            }
            if (email != null) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }
            if (phone != null) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
            }
            if (birthDate != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("birthDate"), birthDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
