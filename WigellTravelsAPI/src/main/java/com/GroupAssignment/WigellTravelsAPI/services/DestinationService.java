package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;
import com.GroupAssignment.WigellTravelsAPI.repositories.DestinationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService implements DestinationServiceInterface{

    private final DestinationRepository destinationRepository;
    private static final Logger logger = LogManager.getLogger(DestinationService.class);


    public DestinationService(DestinationRepository destinationRepository){
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> getAllDestinations(){
        logger.info("Returning all destinations.");
        return destinationRepository.findAll();
    }

    @Override
    public Destination postNewDestination(Destination destination) {
        logger.info("Posting new destination to database.");
        return destinationRepository.save(destination);
    }

    @Override
    public Destination editExistingDestination(Destination destination) {
        logger.info("Edited and uploaded destination to database.");
        return destinationRepository.save(destination);
    }

    @Override
    public void deleteDestinationById(Long id) {
        logger.info("Successfully deleted destination with id: " + id);
        destinationRepository.deleteById(id);
    }

}
