package com.reactit.credit.score.service.criteria;

import com.reactit.credit.score.domain.enumeration.StatusType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.reactit.credit.score.domain.Claim} entity. This class is used
 * in {@link com.reactit.credit.score.web.rest.ClaimResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /claims?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimCriteria implements Serializable, Criteria {

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

    private StatusTypeFilter status;

    private StringFilter title;

    private StringFilter message;

    private InstantFilter createdAt;

    private LongFilter memberUserId;

    private Boolean distinct;

    public ClaimCriteria() {}

    public ClaimCriteria(ClaimCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.memberUserId = other.memberUserId == null ? null : other.memberUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClaimCriteria copy() {
        return new ClaimCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
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
        final ClaimCriteria that = (ClaimCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(title, that.title) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(memberUserId, that.memberUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, title, message, createdAt, memberUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (memberUserId != null ? "memberUserId=" + memberUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
