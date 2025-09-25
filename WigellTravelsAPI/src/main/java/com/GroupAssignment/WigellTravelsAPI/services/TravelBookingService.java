package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import com.GroupAssignment.WigellTravelsAPI.repositories.TravelBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelBookingService implements TravelBookingServiceInterface {

    private final TravelBookingRepository travelBookingRepository;

    public TravelBookingService(TravelBookingRepository travelBookingRepository){
        this.travelBookingRepository = travelBookingRepository;
    }


    @Override
    public List<TravelBooking> getAllAvailableBookings() {
        return List.of();
    }

    @Override
    public List<TravelBooking> getCallerBookingHistory() {
        return List.of();
    }

    @Override
    public List<TravelBooking> getAllCanceledBookings() {
        return List.of();
    }

    @Override
    public List<TravelBooking> getAllScheduledBookings() {
        return List.of();
    }

    @Override
    public List<TravelBooking> getEntireBookingHistory() {
        return List.of();
    }

    @Override
    public TravelBooking postBooking() {
        return null;
    }

    @Override
    public TravelBooking cancelBooking() {
        return null;
    }
}
