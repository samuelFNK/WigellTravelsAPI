package com.GroupAssignment.WigellTravelsAPI.services;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;
import com.GroupAssignment.WigellTravelsAPI.repositories.DestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DestinationServiceUnitTest {

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private DestinationService destinationService;

    private Destination destination;

    @BeforeEach
    void setup() {
        destination = new Destination();
        destination.setId(1L);
        destination.setCity("City Name");
        destination.setCountry("Country Name");
    }


    @Test
    void getAllDestinations_should_return_all_saved_items(){

        List<Destination> mockList = List.of(destination);

        when(destinationRepository.findAll()).thenReturn(mockList);

        List<Destination> result = destinationService.getAllDestinations();

        assertEquals(1, result.size());
        assertEquals("City Name", result.get(0).getCity());
        assertEquals("Country Name", result.get(0).getCountry());
        verify(destinationRepository).findAll();

    }

    @Test
    void getAllDestinations_should_return_empty_array_when_no_destinations_are_saved(){

        List<Destination> mockList = List.of();

        when(destinationRepository.findAll()).thenReturn(mockList);

        List<Destination> result = destinationService.getAllDestinations();

        assertEquals(0, result.size());
        verify(destinationRepository).findAll();

    }

    @Test
    void postNewDestination_should_save_the_entered_destination_to_the_repository(){

        when(destinationRepository.save(destination)).thenReturn(destination);

        Destination result = destinationService.postNewDestination(destination);

        assertEquals("City Name", result.getCity());
        assertEquals("Country Name", result.getCountry());
        assertEquals(1, result.getId());
        verify(destinationRepository).save(destination);
    }

    @Test
    void postNewDestination_should_catch_empty_attribute_fields(){

        destination.setCountry("");
        destination.setCity("");

        assertThrows(IllegalArgumentException.class, () -> {
            destinationService.postNewDestination(destination);
        });
    }

    @Test
    void postNewDestination_should_catch_already_existing_id_trying_to_be_saved(){

        Destination duplicate = new Destination();
        duplicate.setCity("Duplicate city");
        duplicate.setCountry("Duplicate Country");
        duplicate.setId(1L);

        when(destinationRepository.existsById(1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            destinationService.postNewDestination(duplicate);
        });

        verify(destinationRepository).existsById(1L);

    }


    @Test
    void editExistingDestination_should_save_to_database_when_fields_are_filled_correctly(){
        when(destinationRepository.existsById(1L)).thenReturn(true);
        when(destinationRepository.save(destination)).thenReturn(destination);

        Destination result = destinationService.editExistingDestination(destination);

        assertEquals("City Name", result.getCity());
        assertEquals("Country Name", result.getCountry());
        assertEquals(1L, result.getId());
        verify(destinationRepository).existsById(1L);
        verify(destinationRepository).save(destination);
    }

    @Test
    void editExistingDestination_should_catch_id_not_found_in_database(){

        when(destinationRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            destinationService.editExistingDestination(destination);
        });

        verify(destinationRepository).existsById(1L);

    }

    @Test
    void editExistingDestination_should_catch_no_id_being_entered(){

        destination.setId(null);

        assertThrows(IllegalArgumentException.class, () -> {
            destinationService.editExistingDestination(destination);
        });

    }

    @Test
    void deleteDestinationById_should_delete_when_id_exists(){

        when(destinationRepository.existsById(1L)).thenReturn(true);

        destinationService.deleteDestinationById(1L);

        verify(destinationRepository).existsById(1L);
        verify(destinationRepository).deleteById(1L);

    }

    @Test
    void deleteDestinationById_should_catch_id_not_being_provided(){
        assertThrows(IllegalArgumentException.class, () -> {
            destinationService.deleteDestinationById(null);
        });
    }

    @Test
    void deleteDestinationById_should_catch_provided_id_not_being_found_within_database(){

        when(destinationRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            destinationService.deleteDestinationById(1L);
        });

        verify(destinationRepository).existsById(1L);
    }

}
