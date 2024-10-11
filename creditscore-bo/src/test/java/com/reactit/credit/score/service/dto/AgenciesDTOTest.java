package com.reactit.credit.score.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgenciesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenciesDTO.class);
        AgenciesDTO agenciesDTO1 = new AgenciesDTO();
        agenciesDTO1.setId(1L);
        AgenciesDTO agenciesDTO2 = new AgenciesDTO();
        assertThat(agenciesDTO1).isNotEqualTo(agenciesDTO2);
        agenciesDTO2.setId(agenciesDTO1.getId());
        assertThat(agenciesDTO1).isEqualTo(agenciesDTO2);
        agenciesDTO2.setId(2L);
        assertThat(agenciesDTO1).isNotEqualTo(agenciesDTO2);
        agenciesDTO1.setId(null);
        assertThat(agenciesDTO1).isNotEqualTo(agenciesDTO2);
    }
}
