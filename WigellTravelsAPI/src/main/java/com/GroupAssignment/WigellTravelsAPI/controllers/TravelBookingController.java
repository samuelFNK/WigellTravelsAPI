package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import com.GroupAssignment.WigellTravelsAPI.services.CurrencyConverterService;
import com.GroupAssignment.WigellTravelsAPI.services.TravelBookingService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelBookingController {

    public final TravelBookingService travelBookingService;
    public final CurrencyConverterService currencyConverterService;

    public TravelBookingController(TravelBookingService travelBookingService, CurrencyConverterService currencyConverterService){
        this.travelBookingService = travelBookingService;
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/bookings")
    public List<TravelBooking> getAllBookings(){
        List<TravelBooking> bookings = travelBookingService.getAllAvailableBookings();

        bookings.forEach(booking -> {
            BigDecimal euroValue = currencyConverterService.convertSekToEuro(booking.getTotalPrice());
            booking.setTotalPriceEuro(euroValue);
        });

        return bookings;
    }

    @GetMapping("/mybookings")
    public List<TravelBooking> getAllCallerBookings(Principal principal){

        List<TravelBooking> bookings = travelBookingService.getCallerBookingHistory(principal);

        bookings.forEach(booking -> {
            BigDecimal euroValue = currencyConverterService.convertSekToEuro(booking.getTotalPrice());
            booking.setTotalPriceEuro(euroValue);
        });

        return bookings;
    }

    @GetMapping("/listupcoming")
    public List<TravelBooking> getAllUpcomingBookings(){
        return travelBookingService.getAllScheduledBookings();
    }

    @GetMapping("/listcanceled")
    public List<TravelBooking> getAllCanceledBookings(){
        return travelBookingService.getAllCanceledBookings();
    }

    @GetMapping("/listpast")
    public List<TravelBooking> getAllPastBookings(){
        return travelBookingService.getEntireBookingHistory();
    }

    @PostMapping("/booktrip")
    public TravelBooking bookTrip(Principal principal, @RequestBody TravelBooking travelBooking){
        return travelBookingService.postBooking(principal, travelBooking);
    }

    @PutMapping("/canceltrip/{id}")
    public TravelBooking cancelBooking(Principal principal, @PathVariable Long travelBookingID){
        return travelBookingService.cancelBooking(principal, travelBookingID);
    }

}