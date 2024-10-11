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

class BanksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banks.class);
        Banks banks1 = getBanksSample1();
        Banks banks2 = new Banks();
        assertThat(banks1).isNotEqualTo(banks2);

        banks2.setId(banks1.getId());
        assertThat(banks1).isEqualTo(banks2);

        banks2 = getBanksSample2();
        assertThat(banks1).isNotEqualTo(banks2);
    }

    @Test
    void contactTest() throws Exception {
        Banks banks = getBanksRandomSampleGenerator();
        Contact contactBack = getContactRandomSampleGenerator();

        banks.addContact(contactBack);
        assertThat(banks.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getBanks()).isEqualTo(banks);

        banks.removeContact(contactBack);
        assertThat(banks.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getBanks()).isNull();

        banks.contacts(new HashSet<>(Set.of(contactBack)));
        assertThat(banks.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getBanks()).isEqualTo(banks);

        banks.setContacts(new HashSet<>());
        assertThat(banks.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getBanks()).isNull();
    }

    @Test
    void addressTest() throws Exception {
        Banks banks = getBanksRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        banks.addAddress(addressBack);
        assertThat(banks.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getBanks()).isEqualTo(banks);

        banks.removeAddress(addressBack);
        assertThat(banks.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getBanks()).isNull();

        banks.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(banks.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getBanks()).isEqualTo(banks);

        banks.setAddresses(new HashSet<>());
        assertThat(banks.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getBanks()).isNull();
    }

    @Test
    void agenciesTest() throws Exception {
        Banks banks = getBanksRandomSampleGenerator();
        Agencies agenciesBack = getAgenciesRandomSampleGenerator();

        banks.addAgencies(agenciesBack);
        assertThat(banks.getAgencies()).containsOnly(agenciesBack);
        assertThat(agenciesBack.getBanks()).isEqualTo(banks);

        banks.removeAgencies(agenciesBack);
        assertThat(banks.getAgencies()).doesNotContain(agenciesBack);
        assertThat(agenciesBack.getBanks()).isNull();

        banks.agencies(new HashSet<>(Set.of(agenciesBack)));
        assertThat(banks.getAgencies()).containsOnly(agenciesBack);
        assertThat(agenciesBack.getBanks()).isEqualTo(banks);

        banks.setAgencies(new HashSet<>());
        assertThat(banks.getAgencies()).doesNotContain(agenciesBack);
        assertThat(agenciesBack.getBanks()).isNull();
    }
}
