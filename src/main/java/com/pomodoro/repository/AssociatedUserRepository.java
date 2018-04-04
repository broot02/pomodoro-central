package com.pomodoro.repository;

import com.pomodoro.domain.AssociatedUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AssociatedUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociatedUserRepository extends JpaRepository<AssociatedUser, Long> {

}
