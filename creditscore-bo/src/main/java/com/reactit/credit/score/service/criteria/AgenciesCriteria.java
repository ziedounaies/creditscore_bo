package com.reactit.credit.score.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Agencies} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.AgenciesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /agencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenciesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter datefounded;

    private BooleanFilter enabled;

    private InstantFilter createdAt;

    private LongFilter banksId;

    private LongFilter contactId;

    private LongFilter addressId;

    private Boolean distinct;

    public AgenciesCriteria() {}

    public AgenciesCriteria(AgenciesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.datefounded = other.datefounded == null ? null : other.datefounded.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.banksId = other.banksId == null ? null : other.banksId.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AgenciesCriteria copy() {
        return new AgenciesCriteria(this);
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

    public StringFilter getDatefounded() {
        return datefounded;
    }

    public StringFilter datefounded() {
        if (datefounded == null) {
            datefounded = new StringFilter();
        }
        return datefounded;
    }

    public void setDatefounded(StringFilter datefounded) {
        this.datefounded = datefounded;
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

    public LongFilter getBanksId() {
        return banksId;
    }

    public LongFilter banksId() {
        if (banksId == null) {
            banksId = new LongFilter();
        }
        return banksId;
    }

    public void setBanksId(LongFilter banksId) {
        this.banksId = banksId;
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
        final AgenciesCriteria that = (AgenciesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(datefounded, that.datefounded) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(banksId, that.banksId) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, datefounded, enabled, createdAt, banksId, contactId, addressId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenciesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (datefounded != null ? "datefounded=" + datefounded + ", " : "") +
            (enabled != null ? "enabled=" + enabled + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (banksId != null ? "banksId=" + banksId + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
