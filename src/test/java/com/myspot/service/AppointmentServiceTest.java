package com.myspot.service;

import com.myspot.entity.Appointment;
import com.myspot.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAppointmentsForDay() {
        LocalDateTime date = LocalDateTime.now();
        when(appointmentRepository.findByAppointmentTimeBetween(any(), any()))
                .thenReturn(java.util.Collections.emptyList());

        List<Appointment> appointments = appointmentService.getAppointmentsForDay(date);

        assertNotNull(appointments);
        assertTrue(appointments.isEmpty());
        verify(appointmentRepository, times(1))
                .findByAppointmentTimeBetween(any(), any());
    }
}