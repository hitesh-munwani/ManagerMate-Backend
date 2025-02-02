package com.managermate.backend.controller;

import com.managermate.backend.exception.UserNotFoundException;
import com.managermate.backend.model.Attendance;
import com.managermate.backend.model.User;
import com.managermate.backend.service.AttendanceService;
import com.managermate.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @Operation(summary = "Mark attendance for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attendance marked successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/mark/{userId}")
    public Attendance markAttendance(
            @PathVariable Integer userId,
            @RequestParam String address,
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        return attendanceService.markAttendance(user, address, latitude, longitude);
    }

    @Operation(summary = "Get all attendance records for a specific user")
    @GetMapping("/user/{userId}")
    public List<Attendance> getAttendanceByUser(@PathVariable Integer userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        return attendanceService.getAttendanceByUser(user);
    }

    @Operation(summary = "Get attendance records for all employees under a specific manager")
    @GetMapping("/manager/{managerId}")
    public List<Attendance> getAttendanceByManager(@PathVariable Integer managerId) throws UserNotFoundException {
        return attendanceService.getAttendanceByManager(managerId);
    }

    @Operation(summary = "Get attendance for a user within a specific date range")
    @GetMapping("/user/{userId}/date-range")
    public List<Attendance> getAttendanceByUserAndDateRange(
            @PathVariable Integer userId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) throws UserNotFoundException {
        return attendanceService.getAttendanceByUserAndDateRange(userId, start, end);
    }

    @Operation(summary = "Get attendance records based on location (latitude, longitude)")
    @GetMapping("/location")
    public List<Attendance> getAttendanceByLocation(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        return attendanceService.getAttendanceByLocation(latitude, longitude);
    }

    @Operation(summary = "Get all attendance records after a specific time")
    @GetMapping("/after-time")
    public List<Attendance> getAttendanceAfterTime(@RequestParam LocalDateTime startTime) {
        return attendanceService.getAttendanceAfterTime(startTime);
    }
}
