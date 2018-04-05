package com.pomodoro.repository;

import com.pomodoro.domain.DailyTaskList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the DailyTaskList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyTaskListRepository extends JpaRepository<DailyTaskList, Long> {
    @Query("select distinct daily_task_list from DailyTaskList daily_task_list left join fetch daily_task_list.tasks")
    List<DailyTaskList> findAllWithEagerRelationships();

    @Query("select daily_task_list from DailyTaskList daily_task_list left join fetch daily_task_list.tasks where daily_task_list.id =:id")
    DailyTaskList findOneWithEagerRelationships(@Param("id") Long id);

}
