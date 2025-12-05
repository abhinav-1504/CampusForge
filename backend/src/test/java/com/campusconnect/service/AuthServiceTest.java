// 3. AuthServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.RegisterDto;
import com.campusconnect.entity.User;
import com.campusconnect.repository.*;
import com.campusconnect.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;
    @Mock private UniversityRepository universityRepository;
    @Mock private SkillRepository skillRepository;
    @Mock private InterestRepository interestRepository;
    @InjectMocks private AuthService authService;

    @Test void register_EmailAlreadyExists_Throws() {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("taken@campus.com");

        when(userRepository.findByEmail("taken@campus.com")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> authService.register(dto));
    }

    @Test void register_Success() {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("new@campus.com");
        dto.setPassword("pass123");
        dto.setName("Alice");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any())).thenReturn(new User());
        when(jwtUtil.generateToken(any(), any())).thenReturn("jwt-token");

        var response = authService.register(dto);

        assertNotNull(response.getToken());
        verify(userRepository).save(any(User.class));
    }
}