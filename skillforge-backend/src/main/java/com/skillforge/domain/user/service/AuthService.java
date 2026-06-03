package com.skillforge.domain.user.service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already registered", HttpStatus.CONFLICT);
        }

        Role userRole = roleRepository.findByName(RoleConstants.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .provider(AuthProvider.LOCAL)
                .roles(Set.of(userRole))
                .isVerified(false)
                .isActive(true)
                .build();

        User saved = userRepository.save(user);
        return buildAuthResponse(saved);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return buildAuthResponse(user);
    }

    public TokenRefreshResponse refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenService.validateRefreshToken(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(
                new CustomUserDetails(token.getUser()));
        return new TokenRefreshResponse(newAccessToken);
    }

    private AuthResponse buildAuthResponse(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserProfileResponse.from(user))
                .build();
    }
}