package com.reactit.credit.score.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.CreditRapport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CreditRapportDTO implements Serializable {

    private Long id;

    private String creditScore;

    private String accountAge;

    private String creditLimit;

    private String inquiriesAndRequests;

    private Instant createdAt;

    private MemberUserDTO memberUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getAccountAge() {
        return accountAge;
    }

    public void setAccountAge(String accountAge) {
        this.accountAge = accountAge;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getInquiriesAndRequests() {
        return inquiriesAndRequests;
    }

    public void setInquiriesAndRequests(String inquiriesAndRequests) {
        this.inquiriesAndRequests = inquiriesAndRequests;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditRapportDTO)) {
            return false;
        }

        CreditRapportDTO creditRapportDTO = (CreditRapportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creditRapportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditRapportDTO{" +
            "id=" + getId() +
            ", creditScore='" + getCreditScore() + "'" +
            ", accountAge='" + getAccountAge() + "'" +
            ", creditLimit='" + getCreditLimit() + "'" +
            ", inquiriesAndRequests='" + getInquiriesAndRequests() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", memberUser=" + getMemberUser() +
            "}";
    }
}
