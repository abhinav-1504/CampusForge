// 6. CourseServiceImplTest.java
package com.campusconnect.service;

import com.campusconnect.dto.CourseDto;
import com.campusconnect.entity.Course;
import com.campusconnect.entity.Professor;
import com.campusconnect.mapper.CourseMapper;
import com.campusconnect.repository.CourseRepository;
import com.campusconnect.repository.ProfessorRepository;
import com.campusconnect.repository.UniversityRepository;
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
class CourseServiceImplTest {

    @Mock private CourseRepository courseRepository;
    @Mock private ProfessorRepository professorRepository;
    @Mock private UniversityRepository universityRepository;
    @Mock private CourseMapper courseMapper;
    @InjectMocks private CourseServiceImpl service;

    @Test void createCourse_DuplicateNameAndProfessor_Throws() {
        CourseDto dto = new CourseDto();
        dto.setName("Java Programming");
        dto.setProfessorId(1L);

        when(professorRepository.findById(1L)).thenReturn(Optional.of(new Professor()));
        when(courseRepository.existsByNameIgnoreCaseAndProfessor(any(), any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.createCourse(dto));
    }
}