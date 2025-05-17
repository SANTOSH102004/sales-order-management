package com.example.salesordermanagement.service;

import com.example.salesordermanagement.dto.UserDto;
import com.example.salesordermanagement.model.User;
import com.example.salesordermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setJobTitle(userDto.getJobTitle());
        user.setDepartment(userDto.getDepartment());
        user.setProfileImageUrl(userDto.getProfileImageUrl());
        user.setEmailNotifications(userDto.isEmailNotifications());
        user.setSystemNotifications(userDto.isSystemNotifications());
        user.setTwoFactorEnabled(userDto.isTwoFactorEnabled());
        
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .profileImageUrl(user.getProfileImageUrl())
                .jobTitle(user.getJobTitle())
                .department(user.getDepartment())
                .emailNotifications(user.isEmailNotifications())
                .systemNotifications(user.isSystemNotifications())
                .twoFactorEnabled(user.isTwoFactorEnabled())
                .build();
    }
}
