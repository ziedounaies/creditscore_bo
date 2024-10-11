package com.reactit.credit.score.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Banks} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.BanksResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /banks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanksCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter foundedDate;

    private StringFilter branches;

    private BooleanFilter enabled;

    private InstantFilter createdAt;

    private LongFilter contactId;

    private LongFilter addressId;

    private LongFilter agenciesId;

    private Boolean distinct;

    public BanksCriteria() {}

    public BanksCriteria(BanksCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.foundedDate = other.foundedDate == null ? null : other.foundedDate.copy();
        this.branches = other.branches == null ? null : other.branches.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.agenciesId = other.agenciesId == null ? null : other.agenciesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BanksCriteria copy() {
        return new BanksCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public InstantFilter getFoundedDate() {
        return foundedDate;
    }

    public InstantFilter foundedDate() {
        if (foundedDate == null) {
            foundedDate = new InstantFilter();
        }
        return foundedDate;
    }

    public void setFoundedDate(InstantFilter foundedDate) {
        this.foundedDate = foundedDate;
    }

    public StringFilter getBranches() {
        return branches;
    }

    public StringFilter branches() {
        if (branches == null) {
            branches = new StringFilter();
        }
        return branches;
    }

    public void setBranches(StringFilter branches) {
        this.branches = branches;
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

    public LongFilter getAgenciesId() {
        return agenciesId;
    }

    public LongFilter agenciesId() {
        if (agenciesId == null) {
            agenciesId = new LongFilter();
        }
        return agenciesId;
    }

    public void setAgenciesId(LongFilter agenciesId) {
        this.agenciesId = agenciesId;
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
        final BanksCriteria that = (BanksCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(foundedDate, that.foundedDate) &&
            Objects.equals(branches, that.branches) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(agenciesId, that.agenciesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, foundedDate, branches, enabled, createdAt, contactId, addressId, agenciesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (foundedDate != null ? "foundedDate=" + foundedDate + ", " : "") +
            (branches != null ? "branches=" + branches + ", " : "") +
            (enabled != null ? "enabled=" + enabled + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (agenciesId != null ? "agenciesId=" + agenciesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
