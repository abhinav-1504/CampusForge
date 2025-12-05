// 18. TaskServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.TaskRequestDTO;
import com.campusconnect.dto.TaskResponseDTO;
import com.campusconnect.entity.Project;
import com.campusconnect.entity.Task;
import com.campusconnect.entity.User;
import com.campusconnect.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock private TaskRepository taskRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProjectMemberRepository memberRepository;
    @InjectMocks private TaskServiceImpl service;

    @Test void assignTask_UserNotMember_Throws() {
        Task task = new Task();
        task.setProject(new Project());
        User assignee = new User();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(assignee));
        when(memberRepository.findByProjectAndUser(any(), any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.assignTask(1L, 1L));
    }
}