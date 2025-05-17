package com.example.salesordermanagement.dto;

import com.example.salesordermanagement.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String profileImageUrl;
    private String jobTitle;
    private String department;
    private boolean emailNotifications;
    private boolean systemNotifications;
    private boolean twoFactorEnabled;
}
