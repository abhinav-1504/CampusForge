// 9. NotificationServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.NotificationDto;
import com.campusconnect.entity.Notification;
import com.campusconnect.entity.User;
import com.campusconnect.repository.NotificationRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock private NotificationRepository notificationRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private NotificationService service;

    @Test void createNotification_EmptyMessage_Throws() {
        NotificationDto dto = new NotificationDto();
        dto.setUserId(1L);
        assertThrows(IllegalArgumentException.class, () -> service.createNotification(dto));
    }

    @Test void markAsRead_Success() {
        Notification notif = new Notification();
        notif.setRead(false);

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notif));
        when(notificationRepository.save(any())).thenReturn(notif);

        NotificationDto result = service.markAsRead(1L);
        assertTrue(result.isRead());
    }
}