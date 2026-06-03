package com.skillforge.domain.user.controller;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.success(authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(
            @RequestHeader("X-Refresh-Token") String refreshToken) {
        return ResponseEntity.ok(
                ApiResponse.success(authService.refreshToken(refreshToken)));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("X-Refresh-Token") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}