package com.pomodoro.repository;

import com.pomodoro.domain.Task;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select task from Task task where task.username.login = ?#{principal.username}")
    List<Task> findByUsernameIsCurrentUser();

}
