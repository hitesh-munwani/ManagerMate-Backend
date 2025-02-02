package com.managermate.backend.repository;

import com.managermate.backend.model.Attendance;
import com.managermate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    // Find all attendance records by user (employee)
    List<Attendance> findByUser(User user);

    // Find all attendance records for multiple users (e.g., employees under a manager)
    List<Attendance> findAllByUserIn(List<User> users);

    // Find attendance records by user and a specific date range
    List<Attendance> findByUserAndMarkedAtBetween(User user, LocalDateTime start, LocalDateTime end);

    // Find attendance by location (latitude/longitude)
    List<Attendance> findByLatitudeAndLongitude(Double latitude, Double longitude);

    // Find attendance records within a specific time frame
    List<Attendance> findByMarkedAtAfter(LocalDateTime start);
}
