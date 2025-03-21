package com.myspot.controller;

import com.myspot.entity.Appointment;
import com.myspot.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointmentsForDay(
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime date) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDay(date);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    public ResponseEntity<Appointment> scheduleAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.scheduleAppointment(appointment);
        return ResponseEntity.ok(savedAppointment);
    }
}