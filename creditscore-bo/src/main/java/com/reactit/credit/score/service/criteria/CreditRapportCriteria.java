package com.reactit.credit.score.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.CreditRapport} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.CreditRapportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /credit-rapports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditRapportCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter creditScore;

    private StringFilter accountAge;

    private StringFilter creditLimit;

    private StringFilter inquiriesAndRequests;

    private InstantFilter createdAt;

    private LongFilter memberUserId;

    private LongFilter invoiceId;

    private Boolean distinct;

    public CreditRapportCriteria() {}

    public CreditRapportCriteria(CreditRapportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.creditScore = other.creditScore == null ? null : other.creditScore.copy();
        this.accountAge = other.accountAge == null ? null : other.accountAge.copy();
        this.creditLimit = other.creditLimit == null ? null : other.creditLimit.copy();
        this.inquiriesAndRequests = other.inquiriesAndRequests == null ? null : other.inquiriesAndRequests.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.memberUserId = other.memberUserId == null ? null : other.memberUserId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CreditRapportCriteria copy() {
        return new CreditRapportCriteria(this);
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

    public StringFilter getCreditScore() {
        return creditScore;
    }

    public StringFilter creditScore() {
        if (creditScore == null) {
            creditScore = new StringFilter();
        }
        return creditScore;
    }

    public void setCreditScore(StringFilter creditScore) {
        this.creditScore = creditScore;
    }

    public StringFilter getAccountAge() {
        return accountAge;
    }

    public StringFilter accountAge() {
        if (accountAge == null) {
            accountAge = new StringFilter();
        }
        return accountAge;
    }

    public void setAccountAge(StringFilter accountAge) {
        this.accountAge = accountAge;
    }

    public StringFilter getCreditLimit() {
        return creditLimit;
    }

    public StringFilter creditLimit() {
        if (creditLimit == null) {
            creditLimit = new StringFilter();
        }
        return creditLimit;
    }

    public void setCreditLimit(StringFilter creditLimit) {
        this.creditLimit = creditLimit;
    }

    public StringFilter getInquiriesAndRequests() {
        return inquiriesAndRequests;
    }

    public StringFilter inquiriesAndRequests() {
        if (inquiriesAndRequests == null) {
            inquiriesAndRequests = new StringFilter();
        }
        return inquiriesAndRequests;
    }

    public void setInquiriesAndRequests(StringFilter inquiriesAndRequests) {
        this.inquiriesAndRequests = inquiriesAndRequests;
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
        final CreditRapportCriteria that = (CreditRapportCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creditScore, that.creditScore) &&
            Objects.equals(accountAge, that.accountAge) &&
            Objects.equals(creditLimit, that.creditLimit) &&
            Objects.equals(inquiriesAndRequests, that.inquiriesAndRequests) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(memberUserId, that.memberUserId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditScore, accountAge, creditLimit, inquiriesAndRequests, createdAt, memberUserId, invoiceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditRapportCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (creditScore != null ? "creditScore=" + creditScore + ", " : "") +
            (accountAge != null ? "accountAge=" + accountAge + ", " : "") +
            (creditLimit != null ? "creditLimit=" + creditLimit + ", " : "") +
            (inquiriesAndRequests != null ? "inquiriesAndRequests=" + inquiriesAndRequests + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (memberUserId != null ? "memberUserId=" + memberUserId + ", " : "") +
            (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
