package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;

import java.util.List;

public interface TravelBookingServiceInterface {

    List<TravelBooking> getAllAvailableBookings();

    List<TravelBooking> getCallerBookingHistory();

    List<TravelBooking> getAllCanceledBookings();

    List<TravelBooking> getAllScheduledBookings();

    List<TravelBooking> getEntireBookingHistory();

    TravelBooking postBooking();

    TravelBooking cancelBooking();

}
