package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.AddressTestSamples.*;
import static com.reactit.credit.score.domain.ClaimTestSamples.*;
import static com.reactit.credit.score.domain.ContactTestSamples.*;
import static com.reactit.credit.score.domain.CreditRapportTestSamples.*;
import static com.reactit.credit.score.domain.InvoiceTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static com.reactit.credit.score.domain.NotificationTestSamples.*;
import static com.reactit.credit.score.domain.PaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MemberUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberUser.class);
        MemberUser memberUser1 = getMemberUserSample1();
        MemberUser memberUser2 = new MemberUser();
        assertThat(memberUser1).isNotEqualTo(memberUser2);

        memberUser2.setId(memberUser1.getId());
        assertThat(memberUser1).isEqualTo(memberUser2);

        memberUser2 = getMemberUserSample2();
        assertThat(memberUser1).isNotEqualTo(memberUser2);
    }

    @Test
    void creditRapportTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        CreditRapport creditRapportBack = getCreditRapportRandomSampleGenerator();

        memberUser.setCreditRapport(creditRapportBack);
        assertThat(memberUser.getCreditRapport()).isEqualTo(creditRapportBack);
        assertThat(creditRapportBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.creditRapport(null);
        assertThat(memberUser.getCreditRapport()).isNull();
        assertThat(creditRapportBack.getMemberUser()).isNull();
    }

    @Test
    void invoiceTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        memberUser.setInvoice(invoiceBack);
        assertThat(memberUser.getInvoice()).isEqualTo(invoiceBack);
        assertThat(invoiceBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.invoice(null);
        assertThat(memberUser.getInvoice()).isNull();
        assertThat(invoiceBack.getMemberUser()).isNull();
    }

    @Test
    void addressTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        memberUser.addAddress(addressBack);
        assertThat(memberUser.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.removeAddress(addressBack);
        assertThat(memberUser.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getMemberUser()).isNull();

        memberUser.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(memberUser.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.setAddresses(new HashSet<>());
        assertThat(memberUser.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getMemberUser()).isNull();
    }

    @Test
    void paymentTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        memberUser.addPayment(paymentBack);
        assertThat(memberUser.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.removePayment(paymentBack);
        assertThat(memberUser.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getMemberUser()).isNull();

        memberUser.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(memberUser.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.setPayments(new HashSet<>());
        assertThat(memberUser.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getMemberUser()).isNull();
    }

    @Test
    void claimTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        Claim claimBack = getClaimRandomSampleGenerator();

        memberUser.addClaim(claimBack);
        assertThat(memberUser.getClaims()).containsOnly(claimBack);
        assertThat(claimBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.removeClaim(claimBack);
        assertThat(memberUser.getClaims()).doesNotContain(claimBack);
        assertThat(claimBack.getMemberUser()).isNull();

        memberUser.claims(new HashSet<>(Set.of(claimBack)));
        assertThat(memberUser.getClaims()).containsOnly(claimBack);
        assertThat(claimBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.setClaims(new HashSet<>());
        assertThat(memberUser.getClaims()).doesNotContain(claimBack);
        assertThat(claimBack.getMemberUser()).isNull();
    }

    @Test
    void notificationTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        memberUser.addNotification(notificationBack);
        assertThat(memberUser.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.removeNotification(notificationBack);
        assertThat(memberUser.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getMemberUser()).isNull();

        memberUser.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(memberUser.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.setNotifications(new HashSet<>());
        assertThat(memberUser.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getMemberUser()).isNull();
    }

    @Test
    void contactTest() throws Exception {
        MemberUser memberUser = getMemberUserRandomSampleGenerator();
        Contact contactBack = getContactRandomSampleGenerator();

        memberUser.addContact(contactBack);
        assertThat(memberUser.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.removeContact(contactBack);
        assertThat(memberUser.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getMemberUser()).isNull();

        memberUser.contacts(new HashSet<>(Set.of(contactBack)));
        assertThat(memberUser.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getMemberUser()).isEqualTo(memberUser);

        memberUser.setContacts(new HashSet<>());
        assertThat(memberUser.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getMemberUser()).isNull();
    }
}
