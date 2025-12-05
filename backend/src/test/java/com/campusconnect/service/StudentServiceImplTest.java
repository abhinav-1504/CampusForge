// 16. StudentServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.UserDto;
import com.campusconnect.entity.Project;
import com.campusconnect.entity.User;
import com.campusconnect.mapper.UserMapper;
import com.campusconnect.repository.ProjectRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private StudentServiceImpl service;

    @Test void joinProject_AddsStudentToProject() {
        User student = new User();
        student.setUserId(1L);

        Project project = new Project();
        project.setMembers(new HashSet<>()); // ← Fixed: mutable set

        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        service.joinProject(1L, 1L);

        assertTrue(project.getMembers().contains(student));
        verify(projectRepository).save(project);
    }
}