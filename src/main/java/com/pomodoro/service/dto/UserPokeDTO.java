package com.pomodoro.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserPoke entity.
 */
public class UserPokeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String title;

    @Size(max = 2000)
    private String description;

    private Long associatedUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAssociatedUserId() {
        return associatedUserId;
    }

    public void setAssociatedUserId(Long associatedUserId) {
        this.associatedUserId = associatedUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPokeDTO userPokeDTO = (UserPokeDTO) o;
        if(userPokeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPokeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPokeDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
