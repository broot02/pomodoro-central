package com.pomodoro.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AssociatedUser entity.
 */
public class AssociatedUserDTO implements Serializable {

    private Long id;

    private Long userId;

    private String userLogin;

    private Long associatedUserId;

    private String associatedUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getAssociatedUserId() {
        return associatedUserId;
    }

    public void setAssociatedUserId(Long userId) {
        this.associatedUserId = userId;
    }

    public String getAssociatedUserLogin() {
        return associatedUserLogin;
    }

    public void setAssociatedUserLogin(String userLogin) {
        this.associatedUserLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssociatedUserDTO associatedUserDTO = (AssociatedUserDTO) o;
        if(associatedUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), associatedUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssociatedUserDTO{" +
            "id=" + getId() +
            "}";
    }
}
