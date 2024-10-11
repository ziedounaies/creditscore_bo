package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.InvoiceTestSamples.*;
import static com.reactit.credit.score.domain.PaymentTestSamples.*;
import static com.reactit.credit.score.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void invoiceTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        product.setInvoice(invoiceBack);
        assertThat(product.getInvoice()).isEqualTo(invoiceBack);

        product.invoice(null);
        assertThat(product.getInvoice()).isNull();
    }

    @Test
    void paymentTest() throws Exception {
        Product product = getProductRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        product.addPayment(paymentBack);
        assertThat(product.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getProducts()).containsOnly(product);

        product.removePayment(paymentBack);
        assertThat(product.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getProducts()).doesNotContain(product);

        product.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(product.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getProducts()).containsOnly(product);

        product.setPayments(new HashSet<>());
        assertThat(product.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getProducts()).doesNotContain(product);
    }
}
