package com.zentory.inventoryappbackend.auth;

import com.zentory.inventoryappbackend.model.UserAccount;
import com.zentory.inventoryappbackend.repository.UserAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserAccountRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthenticationService(UserAccountRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean register(String username, String rawPassword, String role) {
        if (userRepo.findByUsername(username) != null) return false;
        String hash = encoder.encode(rawPassword);
        UserAccount user = new UserAccount(username, hash, role == null ? "STAFF" : role);
        userRepo.save(user);
        return true;
    }

    public UserAccount authenticate(String username, String rawPassword) {
        UserAccount user = userRepo.findByUsername(username);
        if (user == null) return null;
        if (encoder.matches(rawPassword, user.getPassword())) return user;
        return null;
    }
}
