package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;
import com.GroupAssignment.WigellTravelsAPI.services.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class DestinationController {

    public final DestinationService destinationService;

    public DestinationController(DestinationService destinationService){
        this.destinationService = destinationService;
    }

    @GetMapping("/travels")
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @PostMapping("/addtravel")
    public Destination addNewDestination(@RequestBody Destination destination) {
        return destinationService.postNewDestination(destination);
    }

    @PutMapping("/updatetravel")
    public Destination updateDestination(@RequestBody Destination destination){
        return destinationService.editExistingDestination(destination);
    }

    @DeleteMapping("/removetravel/{id}")
    public void removeDestination(@PathVariable Long id){
        destinationService.deleteDestinationById(id);
    }

}
