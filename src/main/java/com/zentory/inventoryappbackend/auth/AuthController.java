package com.zentory.inventoryappbackend.auth;

import com.zentory.inventoryappbackend.model.UserAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String ERROR_KEY = "error";
    private final AuthenticationService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String role = body.getOrDefault("role", "STAFF");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of(ERROR_KEY, "username and password required"));
        }
        boolean ok = authService.register(username, password, role);
        if (!ok) return ResponseEntity.status(409).body(Map.of(ERROR_KEY, "user exists"));
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of(ERROR_KEY, "username and password required"));
        }
        UserAccount user = authService.authenticate(username, password);
        if (user == null) return ResponseEntity.status(401).body(Map.of(ERROR_KEY, "invalid credentials"));
        String token = jwtUtil.generateToken(user.getUsername());
        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("role", user.getRole());
        return ResponseEntity.ok(resp);
    }
}
