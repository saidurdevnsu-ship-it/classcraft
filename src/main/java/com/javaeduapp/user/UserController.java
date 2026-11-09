package com.javaeduapp.user;

import com.javaeduapp.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserProfile>>> listUsers() {
        return ResponseEntity.ok(ApiResponse.ok(userService.findAll().stream().map(UserProfile::from).toList()));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> getMe(@AuthenticationPrincipal UserDetails principal) {
        User user = userService.findByUsername(principal.getUsername());
        return ResponseEntity.ok(ApiResponse.ok(UserProfile.from(user)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> updateMe(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody UserService.UpdateProfileRequest req) {
        User updated = userService.updateProfile(principal.getUsername(), req);
        return ResponseEntity.ok(ApiResponse.ok(UserProfile.from(updated), "Profile updated"));
    }
}
