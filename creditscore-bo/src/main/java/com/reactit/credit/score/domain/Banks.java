package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Banks.
 */
@Entity
@Table(name = "banks")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Banks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "founded_date")
    private Instant foundedDate;

    @Column(name = "branches")
    private String branches;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banks")
    @JsonIgnoreProperties(value = { "memberUser", "banks", "agencies" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banks")
    @JsonIgnoreProperties(value = { "memberUser", "banks", "agencies" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banks")
    @JsonIgnoreProperties(value = { "banks", "contacts", "addresses" }, allowSetters = true)
    private Set<Agencies> agencies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Banks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Banks name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFoundedDate() {
        return this.foundedDate;
    }

    public Banks foundedDate(Instant foundedDate) {
        this.setFoundedDate(foundedDate);
        return this;
    }

    public void setFoundedDate(Instant foundedDate) {
        this.foundedDate = foundedDate;
    }

    public String getBranches() {
        return this.branches;
    }

    public Banks branches(String branches) {
        this.setBranches(branches);
        return this;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Banks enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Banks createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setBanks(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setBanks(this));
        }
        this.contacts = contacts;
    }

    public Banks contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public Banks addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setBanks(this);
        return this;
    }

    public Banks removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setBanks(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setBanks(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setBanks(this));
        }
        this.addresses = addresses;
    }

    public Banks addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Banks addAddress(Address address) {
        this.addresses.add(address);
        address.setBanks(this);
        return this;
    }

    public Banks removeAddress(Address address) {
        this.addresses.remove(address);
        address.setBanks(null);
        return this;
    }

    public Set<Agencies> getAgencies() {
        return this.agencies;
    }

    public void setAgencies(Set<Agencies> agencies) {
        if (this.agencies != null) {
            this.agencies.forEach(i -> i.setBanks(null));
        }
        if (agencies != null) {
            agencies.forEach(i -> i.setBanks(this));
        }
        this.agencies = agencies;
    }

    public Banks agencies(Set<Agencies> agencies) {
        this.setAgencies(agencies);
        return this;
    }

    public Banks addAgencies(Agencies agencies) {
        this.agencies.add(agencies);
        agencies.setBanks(this);
        return this;
    }

    public Banks removeAgencies(Agencies agencies) {
        this.agencies.remove(agencies);
        agencies.setBanks(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banks)) {
            return false;
        }
        return getId() != null && getId().equals(((Banks) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", foundedDate='" + getFoundedDate() + "'" +
            ", branches='" + getBranches() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
