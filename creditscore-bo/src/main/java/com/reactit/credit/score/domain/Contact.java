package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.credit.score.domain.enumeration.TypeContact;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeContact type;

    @Column(name = "value")
    private String value;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "creditRapport", "invoice", "addresses", "payments", "claims", "notifications", "contacts" },
        allowSetters = true
    )
    private MemberUser memberUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contacts", "addresses", "agencies" }, allowSetters = true)
    private Banks banks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "banks", "contacts", "addresses" }, allowSetters = true)
    private Agencies agencies;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeContact getType() {
        return this.type;
    }

    public Contact type(TypeContact type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeContact type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public Contact value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Contact createdAt(Instant createdAt) {
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

    public Contact memberUser(MemberUser memberUser) {
        this.setMemberUser(memberUser);
        return this;
    }

    public Banks getBanks() {
        return this.banks;
    }

    public void setBanks(Banks banks) {
        this.banks = banks;
    }

    public Contact banks(Banks banks) {
        this.setBanks(banks);
        return this;
    }

    public Agencies getAgencies() {
        return this.agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }

    public Contact agencies(Agencies agencies) {
        this.setAgencies(agencies);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return getId() != null && getId().equals(((Contact) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
