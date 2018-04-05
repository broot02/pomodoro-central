package com.pomodoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DailyTaskList.
 */
@Entity
@Table(name = "daily_task_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DailyTaskList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "task_date", nullable = false)
    private LocalDate taskDate;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "daily_task_list_tasks",
               joinColumns = @JoinColumn(name="daily_task_lists_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tasks_id", referencedColumnName="id"))
    private Set<Task> tasks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public DailyTaskList taskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
        return this;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public DailyTaskList tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public DailyTaskList addTasks(Task task) {
        this.tasks.add(task);
        task.getDailyTaskLists().add(this);
        return this;
    }

    public DailyTaskList removeTasks(Task task) {
        this.tasks.remove(task);
        task.getDailyTaskLists().remove(this);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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
        DailyTaskList dailyTaskList = (DailyTaskList) o;
        if (dailyTaskList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dailyTaskList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DailyTaskList{" +
            "id=" + getId() +
            ", taskDate='" + getTaskDate() + "'" +
            "}";
    }
}
