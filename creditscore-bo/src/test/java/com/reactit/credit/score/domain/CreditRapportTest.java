package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.CreditRapportTestSamples.*;
import static com.reactit.credit.score.domain.InvoiceTestSamples.*;
import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CreditRapportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditRapport.class);
        CreditRapport creditRapport1 = getCreditRapportSample1();
        CreditRapport creditRapport2 = new CreditRapport();
        assertThat(creditRapport1).isNotEqualTo(creditRapport2);

        creditRapport2.setId(creditRapport1.getId());
        assertThat(creditRapport1).isEqualTo(creditRapport2);

        creditRapport2 = getCreditRapportSample2();
        assertThat(creditRapport1).isNotEqualTo(creditRapport2);
    }

    @Test
    void memberUserTest() throws Exception {
        CreditRapport creditRapport = getCreditRapportRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        creditRapport.setMemberUser(memberUserBack);
        assertThat(creditRapport.getMemberUser()).isEqualTo(memberUserBack);

        creditRapport.memberUser(null);
        assertThat(creditRapport.getMemberUser()).isNull();
    }

    @Test
    void invoiceTest() throws Exception {
        CreditRapport creditRapport = getCreditRapportRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        creditRapport.addInvoice(invoiceBack);
        assertThat(creditRapport.getInvoices()).containsOnly(invoiceBack);
        assertThat(invoiceBack.getCreditRapport()).isEqualTo(creditRapport);

        creditRapport.removeInvoice(invoiceBack);
        assertThat(creditRapport.getInvoices()).doesNotContain(invoiceBack);
        assertThat(invoiceBack.getCreditRapport()).isNull();

        creditRapport.invoices(new HashSet<>(Set.of(invoiceBack)));
        assertThat(creditRapport.getInvoices()).containsOnly(invoiceBack);
        assertThat(invoiceBack.getCreditRapport()).isEqualTo(creditRapport);

        creditRapport.setInvoices(new HashSet<>());
        assertThat(creditRapport.getInvoices()).doesNotContain(invoiceBack);
        assertThat(invoiceBack.getCreditRapport()).isNull();
    }
}
