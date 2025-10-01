package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Customer;
import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import com.GroupAssignment.WigellTravelsAPI.repositories.TravelBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TravelBookingService implements TravelBookingServiceInterface {

    private final TravelBookingRepository travelBookingRepository;

    public TravelBookingService(TravelBookingRepository travelBookingRepository){
        this.travelBookingRepository = travelBookingRepository;
    }

    @Override
    public List<TravelBooking> getAllAvailableBookings() {
        return travelBookingRepository.findAll();
    }

    @Override
    public List<TravelBooking> getCallerBookingHistory(Principal principal) {
        String principalName = principal.getName();

        return travelBookingRepository.findByCustomers_Username(principalName);
    }

    @Override
    public List<TravelBooking> getAllCanceledBookings() {
        return travelBookingRepository.findByCurrentlyActive(false);
    }

    @Override
    public List<TravelBooking> getAllScheduledBookings() {
        return travelBookingRepository.findByCurrentlyActive(true);
    }

    @Override
    public List<TravelBooking> getEntireBookingHistory() {
        Date currentDate = new Date();
        return travelBookingRepository.findByDepartureDateLessThanEqual(currentDate);
    }

    @Override
    public TravelBooking postBooking(Principal principal, TravelBooking travelBooking) {

        return null;
    }

    @Override
    public TravelBooking cancelBooking(Principal principal, Long travelBookingId) {

        TravelBooking selectedBooking = travelBookingRepository.findById(travelBookingId)
                .orElseThrow(() -> new EntityNotFoundException("There is currently no booking with the entered ID to delete: '" + travelBookingId + "'."));

        boolean authorityConfirmed = false;

        for (Customer customer : selectedBooking.getCustomers()){
            if (customer.getUsername().equals(principal.getName())){
                authorityConfirmed = true;
                break;
            }
        }

        if (!authorityConfirmed){
            throw new AccessDeniedException("The current user is not authorized to cancel thie entered booking id: '" + travelBookingId + "'.");
        }


        LocalDate currentDate = LocalDate.now();
        LocalDate oneWeekFromCurrentDate = currentDate.plusWeeks(1);
        if (selectedBooking.getDepartureDate().isBefore(oneWeekFromCurrentDate)){
            throw new IllegalStateException("You are not allowed to cancel bookings within a weeks notice of departure. The selected booking departs: '" + selectedBooking.getDepartureDate() + "'.");
        }

        selectedBooking.setCurrentlyActive(false);

        return travelBookingRepository.save(selectedBooking);
    }
}
