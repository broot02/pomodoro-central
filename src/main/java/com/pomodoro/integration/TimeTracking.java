package com.pomodoro.integration;

import com.pomodoro.domain.Action;
import org.springframework.http.ResponseEntity;

public interface TimeTracking<T, R> {

  /**
   * Used to start time tracking for type {@link T t}.
   * 
   * @param t
   *          represents the type that will hold the time tracking information
   * @return {@link ResponseEntity} of type {@link R}
   */
  public ResponseEntity<R> startTimeTracking(T t);

  /**
   * Used to stop time tracking for type {@link T t}.
   * 
   * @param t
   *          represents the type that will hold the time tracking information
   * @return {@link ResponseEntity} of type {@link R}
   */
  public ResponseEntity<R> stopTimeTracking(T t);

  /**
   * Used to track an interval of time according to the associated {@link Action}.
   * 
   * @param t
   *          represents the type that will hold the time tracking information
   * @param a
   *          represents the action associated with the time tracking interval, holds the interval
   *          that needs to be tracked.
   * @return {@link ResponseEntity} of type {@link R}.
   */
  public ResponseEntity<R> createTimeTrackingInterval(T t, Action a);

}
