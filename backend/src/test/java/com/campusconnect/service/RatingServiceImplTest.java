// 13. RatingServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.RatingDto;
import com.campusconnect.entity.Rating;
import com.campusconnect.entity.User;
import com.campusconnect.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock private RatingRepository ratingRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private RatingServiceImpl service;

    @Test
    void createRating_Success() {
        var auth = new UsernamePasswordAuthenticationToken("user@campus.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = new User();
        user.setUserId(1L);
        when(userRepository.findByEmail("user@campus.com")).thenReturn(Optional.of(user));
        when(ratingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RatingDto dto = new RatingDto();
        dto.setRatingValue(5);

        RatingDto result = service.createRating(dto);
        assert result != null;

        SecurityContextHolder.clearContext();
    }
}