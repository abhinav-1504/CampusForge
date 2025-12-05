// 2. AttachmentServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.AttachmentDto;
import com.campusconnect.entity.Attachment;
import com.campusconnect.entity.Project;
import com.campusconnect.entity.User;
import com.campusconnect.repository.AttachmentRepository;
import com.campusconnect.repository.ProjectRepository;
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
class AttachmentServiceImplTest {

    @Mock private AttachmentRepository attachmentRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private AttachmentServiceImpl service;

    @Test void uploadAttachment_EmptyFileName_Throws() {
        AttachmentDto dto = new AttachmentDto();
        dto.setFileName("   ");
        assertThrows(IllegalArgumentException.class, () -> service.uploadAttachment(dto));
    }

    @Test void uploadAttachment_ProjectNotFound_Throws() {
        AttachmentDto dto = new AttachmentDto();
        dto.setFileName("report.pdf");
        dto.setProjectId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.uploadAttachment(dto));
    }

    @Test void uploadAttachment_Success() {
        Project project = new Project();
        project.setProjectId(1L);
        AttachmentDto dto = new AttachmentDto();
        dto.setFileName("file.pdf");
        dto.setProjectId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(attachmentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AttachmentDto result = service.uploadAttachment(dto);
        assertNotNull(result);
        verify(attachmentRepository).save(any(Attachment.class));
    }
}