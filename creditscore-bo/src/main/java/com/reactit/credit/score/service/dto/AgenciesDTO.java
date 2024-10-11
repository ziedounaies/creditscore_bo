package com.reactit.credit.score.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.Agencies} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenciesDTO implements Serializable {

    private Long id;

    private String name;

    private String datefounded;

    private Boolean enabled;

    private Instant createdAt;

    private BanksDTO banks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatefounded() {
        return datefounded;
    }

    public void setDatefounded(String datefounded) {
        this.datefounded = datefounded;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public BanksDTO getBanks() {
        return banks;
    }

    public void setBanks(BanksDTO banks) {
        this.banks = banks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgenciesDTO)) {
            return false;
        }

        AgenciesDTO agenciesDTO = (AgenciesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agenciesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenciesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", datefounded='" + getDatefounded() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", banks=" + getBanks() +
            "}";
    }
}
