package com.reactit.credit.score.service.criteria;

import com.reactit.credit.score.domain.enumeration.StatusType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Invoice} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceCriteria implements Serializable, Criteria {

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

    private StringFilter invoiceNumber;

    private StringFilter amount;

    private StatusTypeFilter status;

    private InstantFilter createdAt;

    private LongFilter memberUserId;

    private LongFilter productId;

    private LongFilter paymentId;

    private LongFilter creditRapportId;

    private Boolean distinct;

    public InvoiceCriteria() {}

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.memberUserId = other.memberUserId == null ? null : other.memberUserId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.creditRapportId = other.creditRapportId == null ? null : other.creditRapportId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
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

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public StringFilter invoiceNumber() {
        if (invoiceNumber == null) {
            invoiceNumber = new StringFilter();
        }
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public LongFilter paymentId() {
        if (paymentId == null) {
            paymentId = new LongFilter();
        }
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getCreditRapportId() {
        return creditRapportId;
    }

    public LongFilter creditRapportId() {
        if (creditRapportId == null) {
            creditRapportId = new LongFilter();
        }
        return creditRapportId;
    }

    public void setCreditRapportId(LongFilter creditRapportId) {
        this.creditRapportId = creditRapportId;
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
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(memberUserId, that.memberUserId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(creditRapportId, that.creditRapportId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceNumber, amount, status, createdAt, memberUserId, productId, paymentId, creditRapportId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (memberUserId != null ? "memberUserId=" + memberUserId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            (creditRapportId != null ? "creditRapportId=" + creditRapportId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
