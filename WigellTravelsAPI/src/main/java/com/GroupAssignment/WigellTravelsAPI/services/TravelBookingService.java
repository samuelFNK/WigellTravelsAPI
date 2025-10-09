package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Customer;
import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import com.GroupAssignment.WigellTravelsAPI.repositories.TravelBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Service
public class TravelBookingService implements TravelBookingServiceInterface {

    private final TravelBookingRepository travelBookingRepository;
    private final CurrencyConverterService currencyConverterService;
    private static final Logger logger = LogManager.getLogger(TravelBookingService.class);

    public TravelBookingService(TravelBookingRepository travelBookingRepository, CurrencyConverterService currencyConverterService){
        this.travelBookingRepository = travelBookingRepository;
        this.currencyConverterService = currencyConverterService;
    }

    @Override
    public List<TravelBooking> getAllAvailableBookings() {
        logger.info("Returning all available travel bookings.");
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

        boolean customerAlreadyAdded = false;
        for (Customer customer : travelBooking.getCustomers()){
            if (customer.getUsername().equalsIgnoreCase(principal.getName())){
                customerAlreadyAdded = true;
                break;
            }
        }

        if (!customerAlreadyAdded){
            Customer newCustomer = new Customer();
            newCustomer.setUsername(principal.getName());
            newCustomer.setPassword(principal.getName());
            newCustomer.setLastName("temp.");

            travelBooking.getCustomers().add(newCustomer);
        }

        travelBooking.setCurrentlyActive(true);
        travelBooking.setTotalPrice(BigDecimal.valueOf(travelBooking.getHotelName().length() * 100).setScale(2, RoundingMode.HALF_UP));
        travelBooking.setTotalPriceEuro(currencyConverterService.convertSekToEuro(travelBooking.getTotalPrice()));

        return travelBookingRepository.save(travelBooking);
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
