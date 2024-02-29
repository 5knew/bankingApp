package com.muratkhan.banking.services.impl;
import com.muratkhan.banking.model.User;
import com.muratkhan.banking.repositories.UserRepository;
import com.muratkhan.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
                return userRepository.findByLogin(login);
            }
        };
    }

    @Transactional
    public void updateUserEmail(Long userId, String newEmail) {
        if (newEmail != null && userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserPhone(Long userId, String newPhone) {
        if (newPhone != null && userRepository.existsByPhone(newPhone)) {
            throw new IllegalArgumentException("Phone is already in use");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPhone(newPhone);
        userRepository.save(user);
    }

    public void updateContactInfo(Long userId, String email, String phone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isEmailEmpty = email == null || email.trim().isEmpty();
        boolean isPhoneEmpty = phone == null || phone.trim().isEmpty();

        if (isEmailEmpty && isPhoneEmpty) {
            throw new IllegalArgumentException("Cannot remove both email and phone. At least one contact method is required.");
        }

        if (!isEmailEmpty || !isPhoneEmpty) {
            if (!isEmailEmpty) {
                user.setEmail(email);
            }
            if (!isPhoneEmpty) {
                user.setPhone(phone);
            }
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("At least one contact method (email or phone) must be provided.");
        }
    }
}
