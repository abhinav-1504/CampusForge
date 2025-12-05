// 11. ProjectMemberServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.entity.Project;
import com.campusconnect.entity.ProjectMember;
import com.campusconnect.entity.User;
import com.campusconnect.repository.ProjectMemberRepository;
import com.campusconnect.repository.ProjectRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectMemberServiceImplTest {

    @Mock private ProjectMemberRepository memberRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private ProjectMemberServiceImpl service;

    @Test void removeMember_NotOwner_ThrowsAccessDenied() {
        Project project = new Project();
        User owner = new User();
        owner.setUserId(999L);
        project.setCreator(owner);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        assertThrows(AccessDeniedException.class, () -> service.removeMember(1L, 2L, 888L));
    }
}