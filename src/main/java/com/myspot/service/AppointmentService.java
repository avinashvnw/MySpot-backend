package com.myspot.service;

import com.myspot.entity.Appointment;
import com.myspot.repository.AppointmentRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment scheduleAppointment(Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.save(appointment);
        sendConfirmationSMS(savedAppointment);
        return savedAppointment;
    }

    public List<Appointment> getAppointmentsForDay(LocalDateTime date) {
        LocalDateTime startOfDay = date.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return appointmentRepository.findByAppointmentTimeBetween(startOfDay, endOfDay);
    }

    private void sendConfirmationSMS(Appointment appointment) {
        String message = String.format("Appointment confirmed for %s at %s",
                appointment.getCustomerName(), appointment.getAppointmentTime().toString());
        Message.creator(
                new PhoneNumber(appointment.getPhoneNumber()),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }
}