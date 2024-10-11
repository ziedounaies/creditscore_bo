package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Agencies.
 */
@Entity
@Table(name = "agencies")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agencies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "datefounded")
    private String datefounded;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contacts", "addresses", "agencies" }, allowSetters = true)
    private Banks banks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agencies")
    @JsonIgnoreProperties(value = { "memberUser", "banks", "agencies" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agencies")
    @JsonIgnoreProperties(value = { "memberUser", "banks", "agencies" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agencies id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Agencies name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatefounded() {
        return this.datefounded;
    }

    public Agencies datefounded(String datefounded) {
        this.setDatefounded(datefounded);
        return this;
    }

    public void setDatefounded(String datefounded) {
        this.datefounded = datefounded;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Agencies enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Agencies createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Banks getBanks() {
        return this.banks;
    }

    public void setBanks(Banks banks) {
        this.banks = banks;
    }

    public Agencies banks(Banks banks) {
        this.setBanks(banks);
        return this;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setAgencies(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setAgencies(this));
        }
        this.contacts = contacts;
    }

    public Agencies contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public Agencies addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setAgencies(this);
        return this;
    }

    public Agencies removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setAgencies(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setAgencies(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setAgencies(this));
        }
        this.addresses = addresses;
    }

    public Agencies addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Agencies addAddress(Address address) {
        this.addresses.add(address);
        address.setAgencies(this);
        return this;
    }

    public Agencies removeAddress(Address address) {
        this.addresses.remove(address);
        address.setAgencies(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agencies)) {
            return false;
        }
        return getId() != null && getId().equals(((Agencies) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agencies{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", datefounded='" + getDatefounded() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
