// 20. UserServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.UserDto;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ProjectMemberRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private ProjectMemberRepository projectMemberRepository;
    @InjectMocks private UserService service;

    @Test void getTeammates_OnlyReturnsStudents() {
        User student = new User(); student.setRole(User.Role.STUDENT);
        User admin = new User(); admin.setRole(User.Role.ADMIN);

        when(userRepository.findAll()).thenReturn(List.of(student, admin));

        List<UserDto> result = service.getTeammates(null, null, null, null);
        assertEquals(1, result.size());
    }

    @Test void updateLastSeen_UpdatesTimestamp() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        service.updateLastSeen(1L);

        assertNotNull(user.getLastSeen());
        verify(userRepository).save(user);
    }
}