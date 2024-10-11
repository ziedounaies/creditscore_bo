package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.AddressTestSamples.*;
import static com.reactit.credit.score.domain.AgenciesTestSamples.*;
import static com.reactit.credit.score.domain.BanksTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void memberUserTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        address.setMemberUser(memberUserBack);
        assertThat(address.getMemberUser()).isEqualTo(memberUserBack);

        address.memberUser(null);
        assertThat(address.getMemberUser()).isNull();
    }

    @Test
    void banksTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        Banks banksBack = getBanksRandomSampleGenerator();

        address.setBanks(banksBack);
        assertThat(address.getBanks()).isEqualTo(banksBack);

        address.banks(null);
        assertThat(address.getBanks()).isNull();
    }

    @Test
    void agenciesTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        Agencies agenciesBack = getAgenciesRandomSampleGenerator();

        address.setAgencies(agenciesBack);
        assertThat(address.getAgencies()).isEqualTo(agenciesBack);

        address.agencies(null);
        assertThat(address.getAgencies()).isNull();
    }
}
