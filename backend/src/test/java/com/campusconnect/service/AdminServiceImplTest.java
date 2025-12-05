// 1. AdminServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.UserDto;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ProjectRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private ProjectRepository projectRepository;
    @InjectMocks private AdminServiceImpl adminService;

    private User user;

    @BeforeEach void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setEmail("john@campus.com");
        user.setRole(User.Role.STUDENT);
    }

    @Test void getAllUsers_ReturnsUserDtoList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> result = adminService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test void updateUserRole_ValidRole_UpdatesAndReturnsDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        UserDto result = adminService.updateUserRole(1L, "ADMIN");

        assertEquals("ADMIN", result.getRole());
        verify(userRepository).save(user);
    }

    @Test void updateUserRole_InvalidRole_ThrowsRuntimeException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(RuntimeException.class, () -> adminService.updateUserRole(1L, "KING"));
    }

    @Test void getDashboardStats_ReturnsCorrectStats() {
        when(userRepository.count()).thenReturn(50L);
        when(userRepository.countByRole(User.Role.STUDENT)).thenReturn(40L);
        when(userRepository.countByRole(User.Role.ADMIN)).thenReturn(10L);
        when(projectRepository.count()).thenReturn(25L);

        Map<String, Object> stats = adminService.getDashboardStats();

        assertEquals(50L, stats.get("totalUsers"));
        assertEquals(40L, stats.get("students"));
        assertEquals(10L, stats.get("admins"));
        assertEquals(25L, stats.get("projects"));
    }

    @Test void deleteProject_NotFound_ThrowsException() {
        when(projectRepository.existsById(999L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> adminService.deleteProject(999L));
    }
}