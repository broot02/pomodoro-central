package com.pomodoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.pomodoro.domain.enumeration.Status;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "status_date")
    private LocalDate statusDate;

    @NotNull
    @Column(name = "estimate", nullable = false)
    private Integer estimate;

    @ManyToOne
    private User username;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private Set<Action> actions = new HashSet<>();

    @ManyToMany(mappedBy = "tasks")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DailyTaskList> dailyTaskLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Task status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getStatusDate() {
        return statusDate;
    }

    public Task statusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public Task estimate(Integer estimate) {
        this.estimate = estimate;
        return this;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public User getUsername() {
        return username;
    }

    public Task username(User user) {
        this.username = user;
        return this;
    }

    public void setUsername(User user) {
        this.username = user;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Task actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Task addActions(Action action) {
        this.actions.add(action);
        action.setTask(this);
        return this;
    }

    public Task removeActions(Action action) {
        this.actions.remove(action);
        action.setTask(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<DailyTaskList> getDailyTaskLists() {
        return dailyTaskLists;
    }

    public Task dailyTaskLists(Set<DailyTaskList> dailyTaskLists) {
        this.dailyTaskLists = dailyTaskLists;
        return this;
    }

    public Task addDailyTaskList(DailyTaskList dailyTaskList) {
        this.dailyTaskLists.add(dailyTaskList);
        dailyTaskList.getTasks().add(this);
        return this;
    }

    public Task removeDailyTaskList(DailyTaskList dailyTaskList) {
        this.dailyTaskLists.remove(dailyTaskList);
        dailyTaskList.getTasks().remove(this);
        return this;
    }

    public void setDailyTaskLists(Set<DailyTaskList> dailyTaskLists) {
        this.dailyTaskLists = dailyTaskLists;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", estimate=" + getEstimate() +
            "}";
    }
}
