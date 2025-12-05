// 4. CollaborationRequestServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.CollaborationRequestDto;
import com.campusconnect.entity.*;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.mapper.CollaborationRequestMapper;
import com.campusconnect.repository.*;
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
class CollaborationRequestServiceImplTest {

    @Mock private CollaborationRequestRepository requestRepository;
    @Mock private ProjectMemberRepository memberRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private CollaborationRequestServiceImpl service;

    @Test void sendRequest_DuplicateRequest_Throws() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(new Project()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(requestRepository.existsByProjectAndStudent(any(), any())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.sendRequest(1L, 1L));
    }

    @Test void respondToRequest_Approve_AddsMember() {
        CollaborationRequest request = new CollaborationRequest();
        request.setStatus(CollaborationRequest.Status.PENDING);

        Project project = new Project();
        project.setProjectId(1L);
        User owner = new User();
        owner.setUserId(1L);
        project.setCreator(owner);

        User student = new User();
        student.setUserId(2L);

        request.setProject(project);
        request.setStudent(student);

        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(memberRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.respondToRequest(1L, 1L, "approve");

        verify(memberRepository).save(any(ProjectMember.class));
        assertEquals(CollaborationRequest.Status.APPROVED, request.getStatus());
    }
}