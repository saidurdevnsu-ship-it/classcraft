package com.javaeduapp.user;

import java.util.List;

/** Safe, API-facing view of a user: no password hash, roles as plain names. */
public record UserProfile(Long id, String username, String email, String fullName,
                          boolean active, List<String> roles) {

    public static UserProfile from(User user) {
        return new UserProfile(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.isActive(),
                user.getRoles().stream().map(Role::getName).sorted().toList());
    }
}
