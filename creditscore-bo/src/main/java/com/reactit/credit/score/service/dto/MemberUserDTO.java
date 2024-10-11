package com.reactit.credit.score.service.dto;

import com.reactit.credit.score.domain.enumeration.AcountType;
import com.reactit.credit.score.domain.enumeration.IdentifierType;
import com.reactit.credit.score.domain.enumeration.Role;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.MemberUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberUserDTO implements Serializable {

    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String businessName;

    private Instant birthDate;

    private AcountType acountType;

    private IdentifierType identifierType;

    private String identifierValue;

    private String employersReported;

    private String income;

    private String expenses;

    private String grossProfit;

    private String netProfitMargin;

    private String debtsObligations;

    private Boolean enabled;

    private Role role;

    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public AcountType getAcountType() {
        return acountType;
    }

    public void setAcountType(AcountType acountType) {
        this.acountType = acountType;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifierValue() {
        return identifierValue;
    }

    public void setIdentifierValue(String identifierValue) {
        this.identifierValue = identifierValue;
    }

    public String getEmployersReported() {
        return employersReported;
    }

    public void setEmployersReported(String employersReported) {
        this.employersReported = employersReported;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getNetProfitMargin() {
        return netProfitMargin;
    }

    public void setNetProfitMargin(String netProfitMargin) {
        this.netProfitMargin = netProfitMargin;
    }

    public String getDebtsObligations() {
        return debtsObligations;
    }

    public void setDebtsObligations(String debtsObligations) {
        this.debtsObligations = debtsObligations;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberUserDTO)) {
            return false;
        }

        MemberUserDTO memberUserDTO = (MemberUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberUserDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", businessName='" + getBusinessName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", acountType='" + getAcountType() + "'" +
            ", identifierType='" + getIdentifierType() + "'" +
            ", identifierValue='" + getIdentifierValue() + "'" +
            ", employersReported='" + getEmployersReported() + "'" +
            ", income='" + getIncome() + "'" +
            ", expenses='" + getExpenses() + "'" +
            ", grossProfit='" + getGrossProfit() + "'" +
            ", netProfitMargin='" + getNetProfitMargin() + "'" +
            ", debtsObligations='" + getDebtsObligations() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", role='" + getRole() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
