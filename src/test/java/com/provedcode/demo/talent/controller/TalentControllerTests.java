package com.provedcode.demo.talent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provedcode.handlers.GlobalControllerAdvice;
import com.provedcode.talent.controller.TalentController;
import com.provedcode.talent.mapper.TalentMapper;
import com.provedcode.talent.model.dto.FullTalentDTO;
import com.provedcode.talent.model.dto.ShortTalentDTO;
import com.provedcode.talent.model.entity.*;
import com.provedcode.talent.service.TalentService;
import com.provedcode.user.repo.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TalentController.class)
@WithMockUser
@ExtendWith(MockitoExtension.class)
public class TalentControllerTests {
    @MockBean
    private TalentService talentService;
    @MockBean
    private GlobalControllerAdvice globalControllerAdvice;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TalentMapper talentMapper;
    private Talent talent;



    @BeforeEach
    void setUP() {
        talent = Talent.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization("test-developer")
                .skills(Set.of(new Skill()))
                .talentAttachedFiles(List.of(new TalentAttachedFile()))
                .talentLinks(List.of(new TalentLink()))
                .talentDescription(new TalentDescription())
                .talentContacts(List.of(new TalentContact()))
                .talentProofs(List.of(new TalentProof()))
                .imageName("")
                .build();
    }

    @Test
    void TalentController_FindTalentsPage_ReturnTalentsPage() throws Exception {
        // Mock talentService.getTalentsPage() method
        Page<Talent> mockPage = new PageImpl<>(List.of(new Talent()), Pageable.unpaged(), 1);
        when(talentService.getTalentsPage(anyInt(), anyInt())).thenReturn(mockPage);

        mockMvc.perform(get("/api/v2/talents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser(roles = "TALENT")
    void TalentController_GetTalent_ReturnFullTalentDTO() throws Exception {
        long talentId = 1L;
        // Mock talentService.getTalentById() method
        when(talentService.getTalentById(talentId)).thenReturn(talent);
        // Mock talentMapper.talentToFullTalentDTO method
        when(talentMapper.talentToFullTalentDTO(talent)).thenReturn(FullTalentDTO.builder().build());

        mockMvc.perform(get("/api/v2/talents/{id}", talentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andDo(MockMvcResultHandlers.print());
    }

}
