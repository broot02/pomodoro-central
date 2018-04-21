package com.pomodoro.repository;

import com.pomodoro.domain.UserPoke;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserPoke entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPokeRepository extends JpaRepository<UserPoke, Long> {

}
