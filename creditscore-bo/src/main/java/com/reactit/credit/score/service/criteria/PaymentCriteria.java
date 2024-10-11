package com.reactit.credit.score.service.criteria;

import com.reactit.credit.score.domain.enumeration.PaymentType;
import com.reactit.credit.score.domain.enumeration.StatusType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Payment} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.PaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {

        public PaymentTypeFilter() {}

        public PaymentTypeFilter(PaymentTypeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentTypeFilter copy() {
            return new PaymentTypeFilter(this);
        }
    }

    /**
     * Class for filtering StatusType
     */
    public static class StatusTypeFilter extends Filter<StatusType> {

        public StatusTypeFilter() {}

        public StatusTypeFilter(StatusTypeFilter filter) {
            super(filter);
        }

        @Override
        public StatusTypeFilter copy() {
            return new StatusTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter checkNumber;

    private StringFilter checkIssuer;

    private StringFilter accountNumber;

    private InstantFilter checkDate;

    private StringFilter recipient;

    private InstantFilter dateOfSignature;

    private PaymentTypeFilter paymentMethod;

    private StringFilter amount;

    private InstantFilter expectedPaymentDate;

    private InstantFilter datePaymentMade;

    private StatusTypeFilter status;

    private StringFilter currency;

    private InstantFilter createdAt;

    private LongFilter memberUserId;

    private LongFilter productId;

    private LongFilter invoiceId;

    private Boolean distinct;

    public PaymentCriteria() {}

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.checkNumber = other.checkNumber == null ? null : other.checkNumber.copy();
        this.checkIssuer = other.checkIssuer == null ? null : other.checkIssuer.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.checkDate = other.checkDate == null ? null : other.checkDate.copy();
        this.recipient = other.recipient == null ? null : other.recipient.copy();
        this.dateOfSignature = other.dateOfSignature == null ? null : other.dateOfSignature.copy();
        this.paymentMethod = other.paymentMethod == null ? null : other.paymentMethod.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.expectedPaymentDate = other.expectedPaymentDate == null ? null : other.expectedPaymentDate.copy();
        this.datePaymentMade = other.datePaymentMade == null ? null : other.datePaymentMade.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.memberUserId = other.memberUserId == null ? null : other.memberUserId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCheckNumber() {
        return checkNumber;
    }

    public StringFilter checkNumber() {
        if (checkNumber == null) {
            checkNumber = new StringFilter();
        }
        return checkNumber;
    }

    public void setCheckNumber(StringFilter checkNumber) {
        this.checkNumber = checkNumber;
    }

    public StringFilter getCheckIssuer() {
        return checkIssuer;
    }

    public StringFilter checkIssuer() {
        if (checkIssuer == null) {
            checkIssuer = new StringFilter();
        }
        return checkIssuer;
    }

    public void setCheckIssuer(StringFilter checkIssuer) {
        this.checkIssuer = checkIssuer;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public StringFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new StringFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public InstantFilter getCheckDate() {
        return checkDate;
    }

    public InstantFilter checkDate() {
        if (checkDate == null) {
            checkDate = new InstantFilter();
        }
        return checkDate;
    }

    public void setCheckDate(InstantFilter checkDate) {
        this.checkDate = checkDate;
    }

    public StringFilter getRecipient() {
        return recipient;
    }

    public StringFilter recipient() {
        if (recipient == null) {
            recipient = new StringFilter();
        }
        return recipient;
    }

    public void setRecipient(StringFilter recipient) {
        this.recipient = recipient;
    }

    public InstantFilter getDateOfSignature() {
        return dateOfSignature;
    }

    public InstantFilter dateOfSignature() {
        if (dateOfSignature == null) {
            dateOfSignature = new InstantFilter();
        }
        return dateOfSignature;
    }

    public void setDateOfSignature(InstantFilter dateOfSignature) {
        this.dateOfSignature = dateOfSignature;
    }

    public PaymentTypeFilter getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentTypeFilter paymentMethod() {
        if (paymentMethod == null) {
            paymentMethod = new PaymentTypeFilter();
        }
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentTypeFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public StringFilter getAmount() {
        return amount;
    }

    public StringFilter amount() {
        if (amount == null) {
            amount = new StringFilter();
        }
        return amount;
    }

    public void setAmount(StringFilter amount) {
        this.amount = amount;
    }

    public InstantFilter getExpectedPaymentDate() {
        return expectedPaymentDate;
    }

    public InstantFilter expectedPaymentDate() {
        if (expectedPaymentDate == null) {
            expectedPaymentDate = new InstantFilter();
        }
        return expectedPaymentDate;
    }

    public void setExpectedPaymentDate(InstantFilter expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
    }

    public InstantFilter getDatePaymentMade() {
        return datePaymentMade;
    }

    public InstantFilter datePaymentMade() {
        if (datePaymentMade == null) {
            datePaymentMade = new InstantFilter();
        }
        return datePaymentMade;
    }

    public void setDatePaymentMade(InstantFilter datePaymentMade) {
        this.datePaymentMade = datePaymentMade;
    }

    public StatusTypeFilter getStatus() {
        return status;
    }

    public StatusTypeFilter status() {
        if (status == null) {
            status = new StatusTypeFilter();
        }
        return status;
    }

    public void setStatus(StatusTypeFilter status) {
        this.status = status;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public StringFilter currency() {
        if (currency == null) {
            currency = new StringFilter();
        }
        return currency;
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public LongFilter getMemberUserId() {
        return memberUserId;
    }

    public LongFilter memberUserId() {
        if (memberUserId == null) {
            memberUserId = new LongFilter();
        }
        return memberUserId;
    }

    public void setMemberUserId(LongFilter memberUserId) {
        this.memberUserId = memberUserId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public LongFilter invoiceId() {
        if (invoiceId == null) {
            invoiceId = new LongFilter();
        }
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(checkNumber, that.checkNumber) &&
            Objects.equals(checkIssuer, that.checkIssuer) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(checkDate, that.checkDate) &&
            Objects.equals(recipient, that.recipient) &&
            Objects.equals(dateOfSignature, that.dateOfSignature) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(expectedPaymentDate, that.expectedPaymentDate) &&
            Objects.equals(datePaymentMade, that.datePaymentMade) &&
            Objects.equals(status, that.status) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(memberUserId, that.memberUserId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            checkNumber,
            checkIssuer,
            accountNumber,
            checkDate,
            recipient,
            dateOfSignature,
            paymentMethod,
            amount,
            expectedPaymentDate,
            datePaymentMade,
            status,
            currency,
            createdAt,
            memberUserId,
            productId,
            invoiceId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (checkNumber != null ? "checkNumber=" + checkNumber + ", " : "") +
            (checkIssuer != null ? "checkIssuer=" + checkIssuer + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (checkDate != null ? "checkDate=" + checkDate + ", " : "") +
            (recipient != null ? "recipient=" + recipient + ", " : "") +
            (dateOfSignature != null ? "dateOfSignature=" + dateOfSignature + ", " : "") +
            (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (expectedPaymentDate != null ? "expectedPaymentDate=" + expectedPaymentDate + ", " : "") +
            (datePaymentMade != null ? "datePaymentMade=" + datePaymentMade + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (currency != null ? "currency=" + currency + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (memberUserId != null ? "memberUserId=" + memberUserId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
