// 17. TaskCommentServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.TaskCommentDTO;
import com.campusconnect.entity.Task;
import com.campusconnect.entity.TaskComment;
import com.campusconnect.entity.User;
import com.campusconnect.repository.TaskCommentRepository;
import com.campusconnect.repository.TaskRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskCommentServiceImplTest {

    @Mock private TaskCommentRepository commentRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private TaskCommentServiceImpl service;

    @Test void addComment_Success() {
        Task task = new Task(); task.setTaskId(1L);
        User user = new User(); user.setName("Alice");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TaskCommentDTO result = service.addComment(1L, 1L, "Looks good!");

        assertEquals("Alice", result.getUsername());
    }
}