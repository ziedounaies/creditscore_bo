package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.AddressTestSamples.*;
import static com.reactit.credit.score.domain.AgenciesTestSamples.*;
import static com.reactit.credit.score.domain.BanksTestSamples.*;
import static com.reactit.credit.score.domain.ContactTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgenciesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agencies.class);
        Agencies agencies1 = getAgenciesSample1();
        Agencies agencies2 = new Agencies();
        assertThat(agencies1).isNotEqualTo(agencies2);

        agencies2.setId(agencies1.getId());
        assertThat(agencies1).isEqualTo(agencies2);

        agencies2 = getAgenciesSample2();
        assertThat(agencies1).isNotEqualTo(agencies2);
    }

    @Test
    void banksTest() throws Exception {
        Agencies agencies = getAgenciesRandomSampleGenerator();
        Banks banksBack = getBanksRandomSampleGenerator();

        agencies.setBanks(banksBack);
        assertThat(agencies.getBanks()).isEqualTo(banksBack);

        agencies.banks(null);
        assertThat(agencies.getBanks()).isNull();
    }

    @Test
    void contactTest() throws Exception {
        Agencies agencies = getAgenciesRandomSampleGenerator();
        Contact contactBack = getContactRandomSampleGenerator();

        agencies.addContact(contactBack);
        assertThat(agencies.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getAgencies()).isEqualTo(agencies);

        agencies.removeContact(contactBack);
        assertThat(agencies.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getAgencies()).isNull();

        agencies.contacts(new HashSet<>(Set.of(contactBack)));
        assertThat(agencies.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getAgencies()).isEqualTo(agencies);

        agencies.setContacts(new HashSet<>());
        assertThat(agencies.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getAgencies()).isNull();
    }

    @Test
    void addressTest() throws Exception {
        Agencies agencies = getAgenciesRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        agencies.addAddress(addressBack);
        assertThat(agencies.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getAgencies()).isEqualTo(agencies);

        agencies.removeAddress(addressBack);
        assertThat(agencies.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getAgencies()).isNull();

        agencies.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(agencies.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getAgencies()).isEqualTo(agencies);

        agencies.setAddresses(new HashSet<>());
        assertThat(agencies.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getAgencies()).isNull();
    }
}
