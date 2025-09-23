package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.services.BookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class BookingController {

    public final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

}