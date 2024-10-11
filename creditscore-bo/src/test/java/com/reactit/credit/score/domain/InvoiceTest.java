package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.CreditRapportTestSamples.*;
import static com.reactit.credit.score.domain.InvoiceTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static com.reactit.credit.score.domain.PaymentTestSamples.*;
import static com.reactit.credit.score.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InvoiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoice.class);
        Invoice invoice1 = getInvoiceSample1();
        Invoice invoice2 = new Invoice();
        assertThat(invoice1).isNotEqualTo(invoice2);

        invoice2.setId(invoice1.getId());
        assertThat(invoice1).isEqualTo(invoice2);

        invoice2 = getInvoiceSample2();
        assertThat(invoice1).isNotEqualTo(invoice2);
    }

    @Test
    void memberUserTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        invoice.setMemberUser(memberUserBack);
        assertThat(invoice.getMemberUser()).isEqualTo(memberUserBack);

        invoice.memberUser(null);
        assertThat(invoice.getMemberUser()).isNull();
    }

    @Test
    void productTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        invoice.addProduct(productBack);
        assertThat(invoice.getProducts()).containsOnly(productBack);
        assertThat(productBack.getInvoice()).isEqualTo(invoice);

        invoice.removeProduct(productBack);
        assertThat(invoice.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getInvoice()).isNull();

        invoice.products(new HashSet<>(Set.of(productBack)));
        assertThat(invoice.getProducts()).containsOnly(productBack);
        assertThat(productBack.getInvoice()).isEqualTo(invoice);

        invoice.setProducts(new HashSet<>());
        assertThat(invoice.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getInvoice()).isNull();
    }

    @Test
    void paymentTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        invoice.addPayment(paymentBack);
        assertThat(invoice.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getInvoice()).isEqualTo(invoice);

        invoice.removePayment(paymentBack);
        assertThat(invoice.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getInvoice()).isNull();

        invoice.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(invoice.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getInvoice()).isEqualTo(invoice);

        invoice.setPayments(new HashSet<>());
        assertThat(invoice.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getInvoice()).isNull();
    }

    @Test
    void creditRapportTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        CreditRapport creditRapportBack = getCreditRapportRandomSampleGenerator();

        invoice.setCreditRapport(creditRapportBack);
        assertThat(invoice.getCreditRapport()).isEqualTo(creditRapportBack);

        invoice.creditRapport(null);
        assertThat(invoice.getCreditRapport()).isNull();
    }
}
