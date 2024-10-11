package com.reactit.credit.score.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.Address} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressDTO implements Serializable {

    private Long id;

    private String street;

    private String city;

    private String zipCode;

    private Instant createdAt;

    private MemberUserDTO memberUser;

    private BanksDTO banks;

    private AgenciesDTO agencies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public BanksDTO getBanks() {
        return banks;
    }

    public void setBanks(BanksDTO banks) {
        this.banks = banks;
    }

    public AgenciesDTO getAgencies() {
        return agencies;
    }

    public void setAgencies(AgenciesDTO agencies) {
        this.agencies = agencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", memberUser=" + getMemberUser() +
            ", banks=" + getBanks() +
            ", agencies=" + getAgencies() +
            "}";
    }
}
