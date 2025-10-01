package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import com.GroupAssignment.WigellTravelsAPI.services.TravelBookingService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelBookingController {

    public final TravelBookingService travelBookingService;

    public TravelBookingController(TravelBookingService travelBookingService){
        this.travelBookingService = travelBookingService;
    }

    @GetMapping("/travels")
    public List<TravelBooking> getAllAvailableBookings(){
        return travelBookingService.getAllAvailableBookings();
    }

    @GetMapping("mybookings")
    public List<TravelBooking> getAllCallerBookings(Principal principal){
        return travelBookingService.getCallerBookingHistory(principal);
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