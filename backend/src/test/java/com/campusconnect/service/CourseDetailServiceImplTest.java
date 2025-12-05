// 5. CourseDetailServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.CourseDetailDto;
import com.campusconnect.entity.CourseDetail;
import com.campusconnect.entity.Professor;
import com.campusconnect.entity.University;
import com.campusconnect.mapper.CourseDetailMapper;
import com.campusconnect.repository.*;
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
class CourseDetailServiceImplTest {

    @Mock private CourseDetailRepository repository;
    @Mock private ProfessorRepository professorRepository;
    @Mock private UniversityRepository universityRepository;
    @Mock private CourseDetailMapper mapper;
    @InjectMocks private CourseDetailServiceImpl service;

    @Test void createCourseDetail_EmptyCode_Throws() {
        CourseDetailDto dto = new CourseDetailDto();
        assertThrows(IllegalArgumentException.class, () -> service.createCourseDetail(dto));
    }

    @Test void updateCourseDetail_Success() {
        CourseDetail existing = new CourseDetail();
        existing.setCourseId(1L);
        CourseDetailDto dto = new CourseDetailDto();
        dto.setName("Updated");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);
        when(mapper.toDto(any())).thenReturn(dto);

        CourseDetailDto result = service.updateCourseDetail(1L, dto);
        assertEquals("Updated", result.getName());
    }
}