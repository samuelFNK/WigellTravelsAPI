package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Booking;

import java.util.List;

public interface BookingServiceInterface {

    List<Booking> getAllAvailableBookings();

    List<Booking> getCallerBookingHistory();

    List<Booking> getAllCanceledBookings();

    List<Booking> getAllScheduledBookings();

    List<Booking> getEntireBookingHistory();

    Booking postBooking();

    Booking cancelBooking();

}
