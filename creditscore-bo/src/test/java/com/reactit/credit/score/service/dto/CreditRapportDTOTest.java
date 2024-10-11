package com.reactit.credit.score.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditRapportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRapportDTO.class);
        CreditRapportDTO creditRapportDTO1 = new CreditRapportDTO();
        creditRapportDTO1.setId(1L);
        CreditRapportDTO creditRapportDTO2 = new CreditRapportDTO();
        assertThat(creditRapportDTO1).isNotEqualTo(creditRapportDTO2);
        creditRapportDTO2.setId(creditRapportDTO1.getId());
        assertThat(creditRapportDTO1).isEqualTo(creditRapportDTO2);
        creditRapportDTO2.setId(2L);
        assertThat(creditRapportDTO1).isNotEqualTo(creditRapportDTO2);
        creditRapportDTO1.setId(null);
        assertThat(creditRapportDTO1).isNotEqualTo(creditRapportDTO2);
    }
}
