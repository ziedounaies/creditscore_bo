package com.reactit.credit.score.service.dto;

import com.reactit.credit.score.domain.enumeration.StatusType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.Claim} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimDTO implements Serializable {

    private Long id;

    private StatusType status;

    private String title;

    private String message;

    private Instant createdAt;

    private MemberUserDTO memberUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(o instanceof ClaimDTO)) {
            return false;
        }

        ClaimDTO claimDTO = (ClaimDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, claimDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", memberUser=" + getMemberUser() +
            "}";
    }
}
