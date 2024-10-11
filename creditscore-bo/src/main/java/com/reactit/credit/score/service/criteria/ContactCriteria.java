package com.reactit.credit.score.service.criteria;

import com.reactit.credit.score.domain.enumeration.TypeContact;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Contact} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.ContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TypeContact
     */
    public static class TypeContactFilter extends Filter<TypeContact> {

        public TypeContactFilter() {}

        public TypeContactFilter(TypeContactFilter filter) {
            super(filter);
        }

        @Override
        public TypeContactFilter copy() {
            return new TypeContactFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TypeContactFilter type;

    private StringFilter value;

    private InstantFilter createdAt;

    private LongFilter memberUserId;

    private LongFilter banksId;

    private LongFilter agenciesId;

    private Boolean distinct;

    public ContactCriteria() {}

    public ContactCriteria(ContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.memberUserId = other.memberUserId == null ? null : other.memberUserId.copy();
        this.banksId = other.banksId == null ? null : other.banksId.copy();
        this.agenciesId = other.agenciesId == null ? null : other.agenciesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContactCriteria copy() {
        return new ContactCriteria(this);
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

    public TypeContactFilter getType() {
        return type;
    }

    public TypeContactFilter type() {
        if (type == null) {
            type = new TypeContactFilter();
        }
        return type;
    }

    public void setType(TypeContactFilter type) {
        this.type = type;
    }

    public StringFilter getValue() {
        return value;
    }

    public StringFilter value() {
        if (value == null) {
            value = new StringFilter();
        }
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
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
        final ContactCriteria that = (ContactCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(value, that.value) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(memberUserId, that.memberUserId) &&
            Objects.equals(banksId, that.banksId) &&
            Objects.equals(agenciesId, that.agenciesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, value, createdAt, memberUserId, banksId, agenciesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (memberUserId != null ? "memberUserId=" + memberUserId + ", " : "") +
            (banksId != null ? "banksId=" + banksId + ", " : "") +
            (agenciesId != null ? "agenciesId=" + agenciesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
