package com.reactit.credit.score.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BanksDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanksDTO.class);
        BanksDTO banksDTO1 = new BanksDTO();
        banksDTO1.setId(1L);
        BanksDTO banksDTO2 = new BanksDTO();
        assertThat(banksDTO1).isNotEqualTo(banksDTO2);
        banksDTO2.setId(banksDTO1.getId());
        assertThat(banksDTO1).isEqualTo(banksDTO2);
        banksDTO2.setId(2L);
        assertThat(banksDTO1).isNotEqualTo(banksDTO2);
        banksDTO1.setId(null);
        assertThat(banksDTO1).isNotEqualTo(banksDTO2);
    }
}
