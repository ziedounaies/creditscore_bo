package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.credit.score.domain.enumeration.PaymentType;
import com.reactit.credit.score.domain.enumeration.StatusType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "check_number")
    private String checkNumber;

    @Column(name = "check_issuer")
    private String checkIssuer;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "check_date")
    private Instant checkDate;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "date_of_signature")
    private Instant dateOfSignature;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentType paymentMethod;

    @Column(name = "amount")
    private String amount;

    @Column(name = "expected_payment_date")
    private Instant expectedPaymentDate;

    @Column(name = "date_payment_made")
    private Instant datePaymentMade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "creditRapport", "invoice", "addresses", "payments", "claims", "notifications", "contacts" },
        allowSetters = true
    )
    private MemberUser memberUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_payment__product",
        joinColumns = @JoinColumn(name = "payment_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties(value = { "invoice", "payments" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "memberUser", "products", "payments", "creditRapport" }, allowSetters = true)
    private Invoice invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckNumber() {
        return this.checkNumber;
    }

    public Payment checkNumber(String checkNumber) {
        this.setCheckNumber(checkNumber);
        return this;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckIssuer() {
        return this.checkIssuer;
    }

    public Payment checkIssuer(String checkIssuer) {
        this.setCheckIssuer(checkIssuer);
        return this;
    }

    public void setCheckIssuer(String checkIssuer) {
        this.checkIssuer = checkIssuer;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public Payment accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getCheckDate() {
        return this.checkDate;
    }

    public Payment checkDate(Instant checkDate) {
        this.setCheckDate(checkDate);
        return this;
    }

    public void setCheckDate(Instant checkDate) {
        this.checkDate = checkDate;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public Payment recipient(String recipient) {
        this.setRecipient(recipient);
        return this;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Instant getDateOfSignature() {
        return this.dateOfSignature;
    }

    public Payment dateOfSignature(Instant dateOfSignature) {
        this.setDateOfSignature(dateOfSignature);
        return this;
    }

    public void setDateOfSignature(Instant dateOfSignature) {
        this.dateOfSignature = dateOfSignature;
    }

    public PaymentType getPaymentMethod() {
        return this.paymentMethod;
    }

    public Payment paymentMethod(PaymentType paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(PaymentType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAmount() {
        return this.amount;
    }

    public Payment amount(String amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Instant getExpectedPaymentDate() {
        return this.expectedPaymentDate;
    }

    public Payment expectedPaymentDate(Instant expectedPaymentDate) {
        this.setExpectedPaymentDate(expectedPaymentDate);
        return this;
    }

    public void setExpectedPaymentDate(Instant expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
    }

    public Instant getDatePaymentMade() {
        return this.datePaymentMade;
    }

    public Payment datePaymentMade(Instant datePaymentMade) {
        this.setDatePaymentMade(datePaymentMade);
        return this;
    }

    public void setDatePaymentMade(Instant datePaymentMade) {
        this.datePaymentMade = datePaymentMade;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public Payment status(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Payment currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Payment createdAt(Instant createdAt) {
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

    public Payment memberUser(MemberUser memberUser) {
        this.setMemberUser(memberUser);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Payment products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Payment addProduct(Product product) {
        this.products.add(product);
        return this;
    }

    public Payment removeProduct(Product product) {
        this.products.remove(product);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Payment invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return getId() != null && getId().equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", checkIssuer='" + getCheckIssuer() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", checkDate='" + getCheckDate() + "'" +
            ", recipient='" + getRecipient() + "'" +
            ", dateOfSignature='" + getDateOfSignature() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", amount='" + getAmount() + "'" +
            ", expectedPaymentDate='" + getExpectedPaymentDate() + "'" +
            ", datePaymentMade='" + getDatePaymentMade() + "'" +
            ", status='" + getStatus() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
