// 15. SkillServiceTest.java
package com.campusconnect.service;

import com.campusconnect.dto.SkillDto;
import com.campusconnect.entity.Skill;
import com.campusconnect.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock private SkillRepository skillRepository;
    @InjectMocks private SkillService service;

    @Test void createSkill_Success() {
        SkillDto dto = new SkillDto();
        dto.setName("React");

        when(skillRepository.save(any())).thenAnswer(i -> {
            Skill s = new Skill();
            s.setSkillId(1L);
            s.setName("React");
            return s;
        });

        SkillDto result = service.createSkill(dto);
        assertEquals("React", result.getName());
    }
}