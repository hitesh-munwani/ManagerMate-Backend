package com.managermate.backend.service;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Attendance;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;

    public Attendance markAttendance(User user, String address, Double latitude, Double longitude) {
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setAddress(address);
        attendance.setLatitude(latitude);
        attendance.setLongitude(longitude);
        attendance.setMarkedAt(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByUser(User user) {
        return attendanceRepository.findByUser(user);
    }

    public List<Attendance> getAttendanceByManager(Integer managerId) throws UserNotFoundException {
        User manager = userService.findUserById(managerId);
        List<User> employees = userService.getEmployeesByManagerId(managerId);
        return attendanceRepository.findAllByUserIn(employees);
    }

    public List<Attendance> getAttendanceByUserAndDateRange(Integer userId, LocalDateTime start, LocalDateTime end) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        return attendanceRepository.findByUserAndMarkedAtBetween(user, start, end);
    }

    public List<Attendance> getAttendanceByLocation(Double latitude, Double longitude) {
        return attendanceRepository.findByLatitudeAndLongitude(latitude, longitude);
    }

    public List<Attendance> getAttendanceAfterTime(LocalDateTime startTime) {
        return attendanceRepository.findByMarkedAtAfter(startTime);
    }
}
