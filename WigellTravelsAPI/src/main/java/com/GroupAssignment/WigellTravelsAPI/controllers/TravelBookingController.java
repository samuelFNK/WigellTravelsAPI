package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import com.GroupAssignment.WigellTravelsAPI.services.TravelBookingService;
import org.springframework.web.bind.annotation.*;

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
    public List<TravelBooking> getAllCallerBookings(){
        return travelBookingService.getCallerBookingHistory();
    }

    @PostMapping("/booktrip")
    public TravelBooking bookTrip(){
        return travelBookingService.postBooking();
    }

    @PutMapping("/canceltrip")
    public TravelBooking cancelBooking(){
        return travelBookingService.cancelBooking();
    }



}