package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;
import com.GroupAssignment.WigellTravelsAPI.repositories.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService implements DestinationServiceInterface{

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository){
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> getAllDestinations(){
        return destinationRepository.findAll();
    }

    @Override
    public Destination postNewDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Override
    public Destination editExistingDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Override
    public void deleteDestinationById(Long id) {
        destinationRepository.deleteById(id);
    }

}
