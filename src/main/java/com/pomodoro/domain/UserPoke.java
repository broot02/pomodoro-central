package com.pomodoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserPoke.
 */
@Entity
@Table(name = "user_poke")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPoke implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private AssociatedUser associatedUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public UserPoke title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public UserPoke description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssociatedUser getAssociatedUser() {
        return associatedUser;
    }

    public UserPoke associatedUser(AssociatedUser associatedUser) {
        this.associatedUser = associatedUser;
        return this;
    }

    public void setAssociatedUser(AssociatedUser associatedUser) {
        this.associatedUser = associatedUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPoke userPoke = (UserPoke) o;
        if (userPoke.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPoke.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPoke{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
