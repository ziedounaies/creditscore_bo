package com.reactit.credit.score.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberUserDTO.class);
        MemberUserDTO memberUserDTO1 = new MemberUserDTO();
        memberUserDTO1.setId(1L);
        MemberUserDTO memberUserDTO2 = new MemberUserDTO();
        assertThat(memberUserDTO1).isNotEqualTo(memberUserDTO2);
        memberUserDTO2.setId(memberUserDTO1.getId());
        assertThat(memberUserDTO1).isEqualTo(memberUserDTO2);
        memberUserDTO2.setId(2L);
        assertThat(memberUserDTO1).isNotEqualTo(memberUserDTO2);
        memberUserDTO1.setId(null);
        assertThat(memberUserDTO1).isNotEqualTo(memberUserDTO2);
    }
}
