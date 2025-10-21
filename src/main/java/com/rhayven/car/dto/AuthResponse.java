package com.rhayven.car.dto;

public record AuthResponse(String token, String username, Long expiresAt) {
}
