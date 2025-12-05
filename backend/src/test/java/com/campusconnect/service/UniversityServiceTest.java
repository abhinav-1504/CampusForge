// 19. UniversityServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.UniversityDto;
import com.campusconnect.entity.University;
import com.campusconnect.repository.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {

    @Mock private UniversityRepository universityRepository;
    @InjectMocks private UniversityService service;

    @Test void createUniversity_Success() {
        UniversityDto dto = UniversityDto.builder().name("Stanford").build();
        when(universityRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UniversityDto result = service.createUniversity(dto);
        assertEquals("Stanford", result.getName());
    }

    @Test void getUniversityById_NotFound_Throws() {
        when(universityRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getUniversityById(999L));
    }
}