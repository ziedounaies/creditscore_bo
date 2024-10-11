package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.credit.score.domain.enumeration.StatusType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Claim.
 */
@Entity
@Table(name = "claim")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "creditRapport", "invoice", "addresses", "payments", "claims", "notifications", "contacts" },
        allowSetters = true
    )
    private MemberUser memberUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Claim id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public Claim status(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public Claim title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public Claim message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Claim createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public MemberUser getMemberUser() {
        return this.memberUser;
    }

    public void setMemberUser(MemberUser memberUser) {
        this.memberUser = memberUser;
    }

    public Claim memberUser(MemberUser memberUser) {
        this.setMemberUser(memberUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Claim)) {
            return false;
        }
        return getId() != null && getId().equals(((Claim) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Claim{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
