package iuh.fit.se.controllers;

import iuh.fit.se.dtos.LoginRequest;
import iuh.fit.se.entities.User;
import iuh.fit.se.security.JwtTokenService;
import iuh.fit.se.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    @Value("${security.jwt.expiration_refresh-ms}")
    private long expirationRefreshMs;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenService.generateAccessToken(userDetails);

        User user = userService.findByUserName(userDetails.getUsername());

        String refreshToken = jwtTokenService.generateRefreshToken(userDetails);
        ResponseCookie cookie = ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(expirationRefreshMs)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("token", accessToken, "user", user));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(@CookieValue(name = "refresh") String refreshToken) {
        UserDetails userDetails = jwtTokenService.validateRefreshToken(refreshToken);
        User user = userService.findByUserName(userDetails.getUsername());
        return ResponseEntity.ok()
                .body(Map.of("token", jwtTokenService.generateAccessToken(userDetails), "user", user));
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout() {
        ResponseCookie cookie = ResponseCookie.from("refresh", null)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(true);
    }
}
