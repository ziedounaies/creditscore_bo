package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.credit.score.domain.enumeration.StatusType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "amount")
    private String amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "created_at")
    private Instant createdAt;

    @JsonIgnoreProperties(
        value = { "creditRapport", "invoice", "addresses", "payments", "claims", "notifications", "contacts" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private MemberUser memberUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    @JsonIgnoreProperties(value = { "invoice", "payments" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    @JsonIgnoreProperties(value = { "memberUser", "products", "invoice" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "memberUser", "invoices" }, allowSetters = true)
    private CreditRapport creditRapport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public Invoice invoiceNumber(String invoiceNumber) {
        this.setInvoiceNumber(invoiceNumber);
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getAmount() {
        return this.amount;
    }

    public Invoice amount(String amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public Invoice status(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Invoice createdAt(Instant createdAt) {
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

    public Invoice memberUser(MemberUser memberUser) {
        this.setMemberUser(memberUser);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setInvoice(null));
        }
        if (products != null) {
            products.forEach(i -> i.setInvoice(this));
        }
        this.products = products;
    }

    public Invoice products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Invoice addProduct(Product product) {
        this.products.add(product);
        product.setInvoice(this);
        return this;
    }

    public Invoice removeProduct(Product product) {
        this.products.remove(product);
        product.setInvoice(null);
        return this;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setInvoice(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setInvoice(this));
        }
        this.payments = payments;
    }

    public Invoice payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public Invoice addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setInvoice(this);
        return this;
    }

    public Invoice removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setInvoice(null);
        return this;
    }

    public CreditRapport getCreditRapport() {
        return this.creditRapport;
    }

    public void setCreditRapport(CreditRapport creditRapport) {
        this.creditRapport = creditRapport;
    }

    public Invoice creditRapport(CreditRapport creditRapport) {
        this.setCreditRapport(creditRapport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return getId() != null && getId().equals(((Invoice) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", amount='" + getAmount() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
