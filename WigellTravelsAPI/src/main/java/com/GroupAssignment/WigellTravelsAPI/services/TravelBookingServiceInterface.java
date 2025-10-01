package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;

import java.security.Principal;
import java.util.List;

public interface TravelBookingServiceInterface {

    List<TravelBooking> getAllAvailableBookings();

    List<TravelBooking> getCallerBookingHistory(Principal principal);

    List<TravelBooking> getAllCanceledBookings();

    List<TravelBooking> getAllScheduledBookings();

    List<TravelBooking> getEntireBookingHistory();

    TravelBooking postBooking(Principal principal, TravelBooking travelBooking);

    TravelBooking cancelBooking(Principal principal, Long travelBookingId);

}
