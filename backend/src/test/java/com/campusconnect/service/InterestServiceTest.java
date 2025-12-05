// 21. InterestServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.InterestDto;
import com.campusconnect.entity.Interest;
import com.campusconnect.repository.InterestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterestServiceTest {

    @Mock private InterestRepository interestRepository;
    @InjectMocks private InterestService service;

    @Test void createInterest_Success() {
        InterestDto dto = new InterestDto();
        dto.setName("Machine Learning");

        when(interestRepository.save(any())).thenAnswer(i -> {
            Interest in = new Interest();
            in.setInterestId(1L);
            in.setName("Machine Learning");
            return in;
        });

        InterestDto result = service.createInterest(dto);
        assertEquals("Machine Learning", result.getName());
    }
}