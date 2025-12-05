// src/test/java/com/campusconnect/service/ReviewServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.ReviewDto;
import com.campusconnect.entity.CourseDetail;
import com.campusconnect.entity.Review;
import com.campusconnect.entity.User;
import com.campusconnect.repository.CourseDetailRepository;
import com.campusconnect.repository.ReviewRepository;
import com.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private UserRepository userRepository;
    @Mock private CourseDetailRepository courseDetailRepository; // ADDED THIS

    @InjectMocks private ReviewServiceImpl service;

    @Test
    void createReview_Success() {
        // Set up security context
        var auth = new UsernamePasswordAuthenticationToken("user@campus.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Mock user
        User user = new User();
        user.setUserId(1L);

        // Mock course detail
        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setCourseId(1L);

        when(userRepository.findByEmail("user@campus.com")).thenReturn(Optional.of(user));
        when(courseDetailRepository.findById(1L)).thenReturn(Optional.of(courseDetail));
        when(reviewRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // DTO with required field
        ReviewDto dto = new ReviewDto();
        dto.setContent("Excellent course!");
        dto.setCourseDetailId(1L); // This prevents the IllegalStateException

        ReviewDto result = service.createReview(dto);

        assert result != null;
        verify(reviewRepository).save(any(Review.class));

        // Clean up
        SecurityContextHolder.clearContext();
    }
}