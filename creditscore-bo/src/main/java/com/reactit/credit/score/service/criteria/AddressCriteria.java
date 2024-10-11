package com.reactit.credit.score.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Address} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.AddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter street;

    private StringFilter city;

    private StringFilter zipCode;

    private InstantFilter createdAt;

    private LongFilter memberUserId;

    private LongFilter banksId;

    private LongFilter agenciesId;

    private Boolean distinct;

    public AddressCriteria() {}

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.zipCode = other.zipCode == null ? null : other.zipCode.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.memberUserId = other.memberUserId == null ? null : other.memberUserId.copy();
        this.banksId = other.banksId == null ? null : other.banksId.copy();
        this.agenciesId = other.agenciesId == null ? null : other.agenciesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AddressCriteria copy() {
        return new AddressCriteria(this);
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

    public StringFilter getStreet() {
        return street;
    }

    public StringFilter street() {
        if (street == null) {
            street = new StringFilter();
        }
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getZipCode() {
        return zipCode;
    }

    public StringFilter zipCode() {
        if (zipCode == null) {
            zipCode = new StringFilter();
        }
        return zipCode;
    }

    public void setZipCode(StringFilter zipCode) {
        this.zipCode = zipCode;
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
        final AddressCriteria that = (AddressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(street, that.street) &&
            Objects.equals(city, that.city) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(memberUserId, that.memberUserId) &&
            Objects.equals(banksId, that.banksId) &&
            Objects.equals(agenciesId, that.agenciesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, zipCode, createdAt, memberUserId, banksId, agenciesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (street != null ? "street=" + street + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (memberUserId != null ? "memberUserId=" + memberUserId + ", " : "") +
            (banksId != null ? "banksId=" + banksId + ", " : "") +
            (agenciesId != null ? "agenciesId=" + agenciesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
