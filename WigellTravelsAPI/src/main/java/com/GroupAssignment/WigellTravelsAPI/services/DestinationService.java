package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;
import com.GroupAssignment.WigellTravelsAPI.repositories.DestinationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        if (!StringUtils.hasText(destination.getCity()) || !StringUtils.hasText(destination.getCountry())){
            logger.warn("Posting destination failed due to all fields not being entered.");
            throw new IllegalArgumentException("Fields not filled in to post destination. ");
        }
        if (destination.getId() != null && destinationRepository.existsById(destination.getId())){
            logger.warn("Posting destination failed due to entered id already existing in database. ");
            throw new IllegalArgumentException("Entered id already exists within destination database.");
        }

        logger.info("Posting new destination to database.");
        return destinationRepository.save(destination);
    }

    @Override
    public Destination editExistingDestination(Destination destination) {
        if (destination.getId() == null){
            logger.warn("Destination editing failed due to no id being entered.");
            throw new IllegalArgumentException("No id was submitted when trying to edit destination.");
        }
        if (!destinationRepository.existsById(destination.getId())){
            logger.warn("Destination editing failed due to entered id not being found within the destination database.");
            throw new IllegalArgumentException("Entered destination id does not exist.");
        }

        logger.info("Edited and uploaded destination to database.");
        return destinationRepository.save(destination);
    }

    @Override
    public void deleteDestinationById(Long id) {

        if (id == null){
            logger.warn("Deleting destination failed due to no id being submitted in the request.");
            throw new IllegalArgumentException("No id entered when trying to delete destination.");
        }
        if (!destinationRepository.existsById(id)){
            logger.warn("Deleting destination failed due to destination id :" + id + " not being found within the database.");
            throw new IllegalArgumentException("Submitted id for deleting destination was not found in database.");
        }

        logger.info("Successfully deleted destination with id: " + id);
        destinationRepository.deleteById(id);
    }

}
