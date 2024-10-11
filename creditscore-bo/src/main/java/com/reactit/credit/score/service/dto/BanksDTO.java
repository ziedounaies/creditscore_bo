package com.reactit.credit.score.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.reactit.credit.score.domain.Banks} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanksDTO implements Serializable {

    private Long id;

    private String name;

    private Instant foundedDate;

    private String branches;

    private Boolean enabled;

    private Instant createdAt;

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

    public Instant getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(Instant foundedDate) {
        this.foundedDate = foundedDate;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BanksDTO)) {
            return false;
        }

        BanksDTO banksDTO = (BanksDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, banksDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", foundedDate='" + getFoundedDate() + "'" +
            ", branches='" + getBranches() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
