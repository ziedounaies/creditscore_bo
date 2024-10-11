package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.InvoiceTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static com.reactit.credit.score.domain.PaymentTestSamples.*;
import static com.reactit.credit.score.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void memberUserTest() throws Exception {
        Payment payment = getPaymentRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        payment.setMemberUser(memberUserBack);
        assertThat(payment.getMemberUser()).isEqualTo(memberUserBack);

        payment.memberUser(null);
        assertThat(payment.getMemberUser()).isNull();
    }

    @Test
    void productTest() throws Exception {
        Payment payment = getPaymentRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        payment.addProduct(productBack);
        assertThat(payment.getProducts()).containsOnly(productBack);

        payment.removeProduct(productBack);
        assertThat(payment.getProducts()).doesNotContain(productBack);

        payment.products(new HashSet<>(Set.of(productBack)));
        assertThat(payment.getProducts()).containsOnly(productBack);

        payment.setProducts(new HashSet<>());
        assertThat(payment.getProducts()).doesNotContain(productBack);
    }

    @Test
    void invoiceTest() throws Exception {
        Payment payment = getPaymentRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        payment.setInvoice(invoiceBack);
        assertThat(payment.getInvoice()).isEqualTo(invoiceBack);

        payment.invoice(null);
        assertThat(payment.getInvoice()).isNull();
    }
}
