package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A CreditRapport.
 */
@Entity
@Table(name = "credit_rapport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditRapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "credit_score")
    private String creditScore;

    @Column(name = "account_age")
    private String accountAge;

    @Column(name = "credit_limit")
    private String creditLimit;

    @Column(name = "inquiries_and_requests")
    private String inquiriesAndRequests;

    @Column(name = "created_at")
    private Instant createdAt;

    @JsonIgnoreProperties(
        value = { "creditRapport", "invoice", "addresses", "payments", "claims", "notifications", "contacts" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private MemberUser memberUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creditRapport")
    @JsonIgnoreProperties(value = { "memberUser", "products", "payments", "creditRapport" }, allowSetters = true)
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CreditRapport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditScore() {
        return this.creditScore;
    }

    public CreditRapport creditScore(String creditScore) {
        this.setCreditScore(creditScore);
        return this;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getAccountAge() {
        return this.accountAge;
    }

    public CreditRapport accountAge(String accountAge) {
        this.setAccountAge(accountAge);
        return this;
    }

    public void setAccountAge(String accountAge) {
        this.accountAge = accountAge;
    }

    public String getCreditLimit() {
        return this.creditLimit;
    }

    public CreditRapport creditLimit(String creditLimit) {
        this.setCreditLimit(creditLimit);
        return this;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getInquiriesAndRequests() {
        return this.inquiriesAndRequests;
    }

    public CreditRapport inquiriesAndRequests(String inquiriesAndRequests) {
        this.setInquiriesAndRequests(inquiriesAndRequests);
        return this;
    }

    public void setInquiriesAndRequests(String inquiriesAndRequests) {
        this.inquiriesAndRequests = inquiriesAndRequests;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public CreditRapport createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public MemberUser getMemberUser() {
        return this.memberUser;
    }

    public void setMemberUser(MemberUser memberUser) {
        this.memberUser = memberUser;
    }

    public CreditRapport memberUser(MemberUser memberUser) {
        this.setMemberUser(memberUser);
        return this;
    }

    public Set<Invoice> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        if (this.invoices != null) {
            this.invoices.forEach(i -> i.setCreditRapport(null));
        }
        if (invoices != null) {
            invoices.forEach(i -> i.setCreditRapport(this));
        }
        this.invoices = invoices;
    }

    public CreditRapport invoices(Set<Invoice> invoices) {
        this.setInvoices(invoices);
        return this;
    }

    public CreditRapport addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCreditRapport(this);
        return this;
    }

    public CreditRapport removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCreditRapport(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditRapport)) {
            return false;
        }
        return getId() != null && getId().equals(((CreditRapport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditRapport{" +
            "id=" + getId() +
            ", creditScore='" + getCreditScore() + "'" +
            ", accountAge='" + getAccountAge() + "'" +
            ", creditLimit='" + getCreditLimit() + "'" +
            ", inquiriesAndRequests='" + getInquiriesAndRequests() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
