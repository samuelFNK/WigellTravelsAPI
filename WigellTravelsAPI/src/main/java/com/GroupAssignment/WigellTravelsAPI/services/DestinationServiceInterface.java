package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;

import java.util.List;

public interface DestinationServiceInterface {

    List<Destination> getAllDestinations();

    Destination postNewDestination(Destination destination);

    Destination editExistingDestination(Destination destination);

    void deleteDestinationById(Long id);

}
