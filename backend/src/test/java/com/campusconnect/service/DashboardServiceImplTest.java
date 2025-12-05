// 7. DashboardServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.DashboardDto;
import com.campusconnect.mapper.*;
import com.campusconnect.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock private ProjectRepository projectRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private CollaborationRequestRepository requestRepository;
    @Mock private MessageRepository messageRepository;
    @Mock private ProjectMapper projectMapper;
    @Mock private TaskMapper taskMapper;
    @Mock private DashboardMapper dashboardMapper;
    @Mock private MessageMapper messageMapper;
    @InjectMocks private DashboardServiceImpl service;

    @Test void getDashboardForUser_ReturnsDto() {
        when(projectRepository.findAllByUserId(1L)).thenReturn(Collections.emptyList());
        when(taskRepository.findByAssignedTo_UserId(1L)).thenReturn(Collections.emptyList());

        DashboardDto result = service.getDashboardForUser(1L);
        assertNotNull(result);
    }
}