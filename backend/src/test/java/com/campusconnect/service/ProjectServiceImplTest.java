package com.campusconnect.service;

import com.campusconnect.dto.ProjectDto;
import com.campusconnect.entity.Project;
import com.campusconnect.entity.User;
import com.campusconnect.mapper.ProjectMapper;
import com.campusconnect.repository.ProjectRepository;
import com.campusconnect.repository.ProjectMemberRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock private ProjectRepository projectRepository;
    @Mock private ProjectMemberRepository memberRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProjectMapper projectMapper;

    @InjectMocks private ProjectServiceImpl service;

    @Test
    void createProject_Success() {
        // Input DTO
        ProjectDto inputDto = new ProjectDto();
        inputDto.setTitle("AI Project");

        User creator = new User();
        creator.setUserId(1L);

        // Mock mapper to return a valid Project
        Project mappedProject = new Project();
        mappedProject.setTitle("AI Project");
        when(projectMapper.toEntity(inputDto)).thenReturn(mappedProject);

        // Mock repository save to return project with ID and creator set
        Project savedProject = new Project();
        savedProject.setProjectId(1L);
        savedProject.setTitle("AI Project");
        savedProject.setCreator(creator);
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> {
            Project p = invocation.getArgument(0);
            p.setProjectId(1L);
            p.setCreator(creator);
            return p;
        });

        when(userRepository.findById(1L)).thenReturn(Optional.of(creator));

        // This is KEY: mock mapper.toDto() to return the SAME inputDto
        when(projectMapper.toDto(any(Project.class))).thenAnswer(invocation -> {
            Project p = invocation.getArgument(0);
            ProjectDto dto = new ProjectDto();
            dto.setProjectId(p.getProjectId());
            dto.setTitle(p.getTitle());
            return dto;
        });

        // Call the method
        ProjectDto result = service.createProject(inputDto, 1L);

        // Now it works — no NPE!
        assertNotNull(result);
        assertEquals(1L, result.getProjectId());
        assertEquals("AI Project", result.getTitle());

        verify(projectRepository).save(any(Project.class));
    }
}