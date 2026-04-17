package com.codewithmosh.store.auth;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private JwtResponse jwtResponse;
    private Cookie refeshTokenCookie;
}
