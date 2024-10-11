package com.reactit.credit.score.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimDTO.class);
        ClaimDTO claimDTO1 = new ClaimDTO();
        claimDTO1.setId(1L);
        ClaimDTO claimDTO2 = new ClaimDTO();
        assertThat(claimDTO1).isNotEqualTo(claimDTO2);
        claimDTO2.setId(claimDTO1.getId());
        assertThat(claimDTO1).isEqualTo(claimDTO2);
        claimDTO2.setId(2L);
        assertThat(claimDTO1).isNotEqualTo(claimDTO2);
        claimDTO1.setId(null);
        assertThat(claimDTO1).isNotEqualTo(claimDTO2);
    }
}
