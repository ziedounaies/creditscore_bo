package com.reactit.credit.score.service.criteria;

import com.reactit.credit.score.domain.enumeration.AcountType;
import com.reactit.credit.score.domain.enumeration.IdentifierType;
import com.reactit.credit.score.domain.enumeration.Role;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.MemberUser} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.MemberUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberUserCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AcountType
     */
    public static class AcountTypeFilter extends Filter<AcountType> {

        public AcountTypeFilter() {}

        public AcountTypeFilter(AcountTypeFilter filter) {
            super(filter);
        }

        @Override
        public AcountTypeFilter copy() {
            return new AcountTypeFilter(this);
        }
    }

    /**
     * Class for filtering IdentifierType
     */
    public static class IdentifierTypeFilter extends Filter<IdentifierType> {

        public IdentifierTypeFilter() {}

        public IdentifierTypeFilter(IdentifierTypeFilter filter) {
            super(filter);
        }

        @Override
        public IdentifierTypeFilter copy() {
            return new IdentifierTypeFilter(this);
        }
    }

    /**
     * Class for filtering Role
     */
    public static class RoleFilter extends Filter<Role> {

        public RoleFilter() {}

        public RoleFilter(RoleFilter filter) {
            super(filter);
        }

        @Override
        public RoleFilter copy() {
            return new RoleFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter businessName;

    private InstantFilter birthDate;

    private AcountTypeFilter acountType;

    private IdentifierTypeFilter identifierType;

    private StringFilter identifierValue;

    private StringFilter employersReported;

    private StringFilter income;

    private StringFilter expenses;

    private StringFilter grossProfit;

    private StringFilter netProfitMargin;

    private StringFilter debtsObligations;

    private BooleanFilter enabled;

    private RoleFilter role;

    private InstantFilter createdAt;

    private LongFilter creditRapportId;

    private LongFilter invoiceId;

    private LongFilter addressId;

    private LongFilter paymentId;

    private LongFilter claimId;

    private LongFilter notificationId;

    private LongFilter contactId;

    private Boolean distinct;

    public MemberUserCriteria() {}

    public MemberUserCriteria(MemberUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.businessName = other.businessName == null ? null : other.businessName.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.acountType = other.acountType == null ? null : other.acountType.copy();
        this.identifierType = other.identifierType == null ? null : other.identifierType.copy();
        this.identifierValue = other.identifierValue == null ? null : other.identifierValue.copy();
        this.employersReported = other.employersReported == null ? null : other.employersReported.copy();
        this.income = other.income == null ? null : other.income.copy();
        this.expenses = other.expenses == null ? null : other.expenses.copy();
        this.grossProfit = other.grossProfit == null ? null : other.grossProfit.copy();
        this.netProfitMargin = other.netProfitMargin == null ? null : other.netProfitMargin.copy();
        this.debtsObligations = other.debtsObligations == null ? null : other.debtsObligations.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.creditRapportId = other.creditRapportId == null ? null : other.creditRapportId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.claimId = other.claimId == null ? null : other.claimId.copy();
        this.notificationId = other.notificationId == null ? null : other.notificationId.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberUserCriteria copy() {
        return new MemberUserCriteria(this);
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

    public StringFilter getUserName() {
        return userName;
    }

    public StringFilter userName() {
        if (userName == null) {
            userName = new StringFilter();
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getBusinessName() {
        return businessName;
    }

    public StringFilter businessName() {
        if (businessName == null) {
            businessName = new StringFilter();
        }
        return businessName;
    }

    public void setBusinessName(StringFilter businessName) {
        this.businessName = businessName;
    }

    public InstantFilter getBirthDate() {
        return birthDate;
    }

    public InstantFilter birthDate() {
        if (birthDate == null) {
            birthDate = new InstantFilter();
        }
        return birthDate;
    }

    public void setBirthDate(InstantFilter birthDate) {
        this.birthDate = birthDate;
    }

    public AcountTypeFilter getAcountType() {
        return acountType;
    }

    public AcountTypeFilter acountType() {
        if (acountType == null) {
            acountType = new AcountTypeFilter();
        }
        return acountType;
    }

    public void setAcountType(AcountTypeFilter acountType) {
        this.acountType = acountType;
    }

    public IdentifierTypeFilter getIdentifierType() {
        return identifierType;
    }

    public IdentifierTypeFilter identifierType() {
        if (identifierType == null) {
            identifierType = new IdentifierTypeFilter();
        }
        return identifierType;
    }

    public void setIdentifierType(IdentifierTypeFilter identifierType) {
        this.identifierType = identifierType;
    }

    public StringFilter getIdentifierValue() {
        return identifierValue;
    }

    public StringFilter identifierValue() {
        if (identifierValue == null) {
            identifierValue = new StringFilter();
        }
        return identifierValue;
    }

    public void setIdentifierValue(StringFilter identifierValue) {
        this.identifierValue = identifierValue;
    }

    public StringFilter getEmployersReported() {
        return employersReported;
    }

    public StringFilter employersReported() {
        if (employersReported == null) {
            employersReported = new StringFilter();
        }
        return employersReported;
    }

    public void setEmployersReported(StringFilter employersReported) {
        this.employersReported = employersReported;
    }

    public StringFilter getIncome() {
        return income;
    }

    public StringFilter income() {
        if (income == null) {
            income = new StringFilter();
        }
        return income;
    }

    public void setIncome(StringFilter income) {
        this.income = income;
    }

    public StringFilter getExpenses() {
        return expenses;
    }

    public StringFilter expenses() {
        if (expenses == null) {
            expenses = new StringFilter();
        }
        return expenses;
    }

    public void setExpenses(StringFilter expenses) {
        this.expenses = expenses;
    }

    public StringFilter getGrossProfit() {
        return grossProfit;
    }

    public StringFilter grossProfit() {
        if (grossProfit == null) {
            grossProfit = new StringFilter();
        }
        return grossProfit;
    }

    public void setGrossProfit(StringFilter grossProfit) {
        this.grossProfit = grossProfit;
    }

    public StringFilter getNetProfitMargin() {
        return netProfitMargin;
    }

    public StringFilter netProfitMargin() {
        if (netProfitMargin == null) {
            netProfitMargin = new StringFilter();
        }
        return netProfitMargin;
    }

    public void setNetProfitMargin(StringFilter netProfitMargin) {
        this.netProfitMargin = netProfitMargin;
    }

    public StringFilter getDebtsObligations() {
        return debtsObligations;
    }

    public StringFilter debtsObligations() {
        if (debtsObligations == null) {
            debtsObligations = new StringFilter();
        }
        return debtsObligations;
    }

    public void setDebtsObligations(StringFilter debtsObligations) {
        this.debtsObligations = debtsObligations;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public BooleanFilter enabled() {
        if (enabled == null) {
            enabled = new BooleanFilter();
        }
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public RoleFilter getRole() {
        return role;
    }

    public RoleFilter role() {
        if (role == null) {
            role = new RoleFilter();
        }
        return role;
    }

    public void setRole(RoleFilter role) {
        this.role = role;
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

    public LongFilter getAddressId() {
        return addressId;
    }

    public LongFilter addressId() {
        if (addressId == null) {
            addressId = new LongFilter();
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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

    public LongFilter getClaimId() {
        return claimId;
    }

    public LongFilter claimId() {
        if (claimId == null) {
            claimId = new LongFilter();
        }
        return claimId;
    }

    public void setClaimId(LongFilter claimId) {
        this.claimId = claimId;
    }

    public LongFilter getNotificationId() {
        return notificationId;
    }

    public LongFilter notificationId() {
        if (notificationId == null) {
            notificationId = new LongFilter();
        }
        return notificationId;
    }

    public void setNotificationId(LongFilter notificationId) {
        this.notificationId = notificationId;
    }

    public LongFilter getContactId() {
        return contactId;
    }

    public LongFilter contactId() {
        if (contactId == null) {
            contactId = new LongFilter();
        }
        return contactId;
    }

    public void setContactId(LongFilter contactId) {
        this.contactId = contactId;
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
        final MemberUserCriteria that = (MemberUserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(businessName, that.businessName) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(acountType, that.acountType) &&
            Objects.equals(identifierType, that.identifierType) &&
            Objects.equals(identifierValue, that.identifierValue) &&
            Objects.equals(employersReported, that.employersReported) &&
            Objects.equals(income, that.income) &&
            Objects.equals(expenses, that.expenses) &&
            Objects.equals(grossProfit, that.grossProfit) &&
            Objects.equals(netProfitMargin, that.netProfitMargin) &&
            Objects.equals(debtsObligations, that.debtsObligations) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(role, that.role) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(creditRapportId, that.creditRapportId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(claimId, that.claimId) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userName,
            firstName,
            lastName,
            businessName,
            birthDate,
            acountType,
            identifierType,
            identifierValue,
            employersReported,
            income,
            expenses,
            grossProfit,
            netProfitMargin,
            debtsObligations,
            enabled,
            role,
            createdAt,
            creditRapportId,
            invoiceId,
            addressId,
            paymentId,
            claimId,
            notificationId,
            contactId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberUserCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userName != null ? "userName=" + userName + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (businessName != null ? "businessName=" + businessName + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (acountType != null ? "acountType=" + acountType + ", " : "") +
            (identifierType != null ? "identifierType=" + identifierType + ", " : "") +
            (identifierValue != null ? "identifierValue=" + identifierValue + ", " : "") +
            (employersReported != null ? "employersReported=" + employersReported + ", " : "") +
            (income != null ? "income=" + income + ", " : "") +
            (expenses != null ? "expenses=" + expenses + ", " : "") +
            (grossProfit != null ? "grossProfit=" + grossProfit + ", " : "") +
            (netProfitMargin != null ? "netProfitMargin=" + netProfitMargin + ", " : "") +
            (debtsObligations != null ? "debtsObligations=" + debtsObligations + ", " : "") +
            (enabled != null ? "enabled=" + enabled + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (creditRapportId != null ? "creditRapportId=" + creditRapportId + ", " : "") +
            (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            (claimId != null ? "claimId=" + claimId + ", " : "") +
            (notificationId != null ? "notificationId=" + notificationId + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
