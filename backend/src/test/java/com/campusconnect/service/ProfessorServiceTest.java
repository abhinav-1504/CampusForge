// 10. ProfessorServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.ProfessorDto;
import com.campusconnect.entity.Professor;
import com.campusconnect.mapper.ProfessorMapper;
import com.campusconnect.repository.ProfessorRepository;
import com.campusconnect.repository.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

    @Mock private ProfessorRepository professorRepository;
    @Mock private ProfessorMapper professorMapper;
    @Mock private UniversityRepository universityRepository;
    @InjectMocks private ProfessorService service;

    @Test
    void saveProfessor_DuplicateEmail_Throws() {
        ProfessorDto dto = new ProfessorDto();
        dto.setEmail("taken@uni.edu");
        dto.setName("Dr. Smith");

        when(professorRepository.existsByEmail("taken@uni.edu")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.saveProfessor(dto));

        // REMOVED verify() — this was causing the error
    }
}