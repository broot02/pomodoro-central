package com.pomodoro.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DailyTaskList entity.
 */
public class DailyTaskListDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate taskDate;

    private Set<TaskDTO> tasks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public Set<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DailyTaskListDTO dailyTaskListDTO = (DailyTaskListDTO) o;
        if(dailyTaskListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dailyTaskListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DailyTaskListDTO{" +
            "id=" + getId() +
            ", taskDate='" + getTaskDate() + "'" +
            "}";
    }
}
