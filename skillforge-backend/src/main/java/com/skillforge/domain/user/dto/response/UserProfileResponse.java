package com.skillforge.domain.user.dto.response;

import com.skillforge.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserProfileResponse {

    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String provider;
    private Boolean isVerified;
    private Set<String> roles;
    private LocalDateTime createdAt;

    // Static factory — keeps mapping logic out of services
    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .provider(user.getProvider().name())
                .isVerified(user.getIsVerified())
                .roles(user.getRoles().stream()
                        .map(r -> r.getName().name())
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .build();
    }
}