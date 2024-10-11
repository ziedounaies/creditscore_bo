package com.reactit.credit.score.service.dto;

import com.reactit.credit.score.domain.enumeration.PaymentType;
import com.reactit.credit.score.domain.enumeration.StatusType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDTO implements Serializable {

    private Long id;

    private String checkNumber;

    private String checkIssuer;

    private String accountNumber;

    private Instant checkDate;

    private String recipient;

    private Instant dateOfSignature;

    private PaymentType paymentMethod;

    private String amount;

    private Instant expectedPaymentDate;

    private Instant datePaymentMade;

    private StatusType status;

    private String currency;

    private Instant createdAt;

    private MemberUserDTO memberUser;

    private Set<ProductDTO> products = new HashSet<>();

    private InvoiceDTO invoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckIssuer() {
        return checkIssuer;
    }

    public void setCheckIssuer(String checkIssuer) {
        this.checkIssuer = checkIssuer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Instant checkDate) {
        this.checkDate = checkDate;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Instant getDateOfSignature() {
        return dateOfSignature;
    }

    public void setDateOfSignature(Instant dateOfSignature) {
        this.dateOfSignature = dateOfSignature;
    }

    public PaymentType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Instant getExpectedPaymentDate() {
        return expectedPaymentDate;
    }

    public void setExpectedPaymentDate(Instant expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
    }

    public Instant getDatePaymentMade() {
        return datePaymentMade;
    }

    public void setDatePaymentMade(Instant datePaymentMade) {
        this.datePaymentMade = datePaymentMade;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public MemberUserDTO getMemberUser() {
        return memberUser;
    }

    public void setMemberUser(MemberUserDTO memberUser) {
        this.memberUser = memberUser;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
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
            ", memberUser=" + getMemberUser() +
            ", products=" + getProducts() +
            ", invoice=" + getInvoice() +
            "}";
    }
}
