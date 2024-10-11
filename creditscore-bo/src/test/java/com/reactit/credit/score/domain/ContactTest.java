package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.AgenciesTestSamples.*;
import static com.reactit.credit.score.domain.BanksTestSamples.*;
import static com.reactit.credit.score.domain.ContactTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contact.class);
        Contact contact1 = getContactSample1();
        Contact contact2 = new Contact();
        assertThat(contact1).isNotEqualTo(contact2);

        contact2.setId(contact1.getId());
        assertThat(contact1).isEqualTo(contact2);

        contact2 = getContactSample2();
        assertThat(contact1).isNotEqualTo(contact2);
    }

    @Test
    void memberUserTest() throws Exception {
        Contact contact = getContactRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        contact.setMemberUser(memberUserBack);
        assertThat(contact.getMemberUser()).isEqualTo(memberUserBack);

        contact.memberUser(null);
        assertThat(contact.getMemberUser()).isNull();
    }

    @Test
    void banksTest() throws Exception {
        Contact contact = getContactRandomSampleGenerator();
        Banks banksBack = getBanksRandomSampleGenerator();

        contact.setBanks(banksBack);
        assertThat(contact.getBanks()).isEqualTo(banksBack);

        contact.banks(null);
        assertThat(contact.getBanks()).isNull();
    }

    @Test
    void agenciesTest() throws Exception {
        Contact contact = getContactRandomSampleGenerator();
        Agencies agenciesBack = getAgenciesRandomSampleGenerator();

        contact.setAgencies(agenciesBack);
        assertThat(contact.getAgencies()).isEqualTo(agenciesBack);

        contact.agencies(null);
        assertThat(contact.getAgencies()).isNull();
    }
}
