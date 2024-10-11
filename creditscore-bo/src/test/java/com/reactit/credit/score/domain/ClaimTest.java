package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.ClaimTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Claim.class);
        Claim claim1 = getClaimSample1();
        Claim claim2 = new Claim();
        assertThat(claim1).isNotEqualTo(claim2);

        claim2.setId(claim1.getId());
        assertThat(claim1).isEqualTo(claim2);

        claim2 = getClaimSample2();
        assertThat(claim1).isNotEqualTo(claim2);
    }

    @Test
    void memberUserTest() throws Exception {
        Claim claim = getClaimRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        claim.setMemberUser(memberUserBack);
        assertThat(claim.getMemberUser()).isEqualTo(memberUserBack);

        claim.memberUser(null);
        assertThat(claim.getMemberUser()).isNull();
    }
}
