package com.muratkhan.banking.repositories;
import com.muratkhan.banking.model.User;
import com.muratkhan.banking.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{


    User findByEmail(String email);
    User findByLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByLogin(String login);
    User findByRole(Role role);
}
