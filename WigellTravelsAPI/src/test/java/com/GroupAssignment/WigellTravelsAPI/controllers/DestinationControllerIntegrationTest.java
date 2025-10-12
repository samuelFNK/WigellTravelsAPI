package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.entities.Destination;
import com.GroupAssignment.WigellTravelsAPI.repositories.DestinationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DestinationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DestinationRepository destinationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Destination destination;

    @BeforeEach
    void setup(){
        destinationRepository.deleteAll();
        destination = new Destination();
        destination.setCity("City Name");
        destination.setCountry("Country Name");
    }


    @WithMockUser(username = "test user", roles = {"USER"})
    @Test
    void getAllDestinations_should_return_code_200_and_list() throws Exception {

        destinationRepository.save(destination);

        mockMvc.perform(get("/api/wigelltravels/v1/travels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("City Name"))
                .andExpect(jsonPath("$[0].country").value("Country Name"));
    }

    @WithMockUser(username = "test user", roles = {"USER"})
    @Test
    void getAllDestinations_should_return_empty_array_when_db_is_empty() throws Exception {

        mockMvc.perform(get("/api/wigelltravels/v1/travels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));


    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void addNewDestination_should_return_code_201_and_save() throws Exception {

        mockMvc.perform(post("/api/wigelltravels/v1/addtravel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destination)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("City Name"))
                .andExpect(jsonPath("$.country").value("Country Name"));
    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void addNewDestination_should_return_code_400_when_fields_empty() throws Exception {

        destination.setCity("");
        destination.setCountry("");

        mockMvc.perform(post("/api/wigelltravels/v1/addtravel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destination)))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void updateDestination_should_return_code_200_and_update() throws Exception {

        destinationRepository.save(destination);
        destination.setCity("Test City");
        destination.setCountry("Test Country");

        mockMvc.perform(put("/api/wigelltravels/v1/updatetravel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destination)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("Test Country"))
                .andExpect(jsonPath("$.city").value("Test City"));

    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void updateDestination_should_return_code_400_when_id_missing() throws Exception {

        destination.setId(null);

        mockMvc.perform(put("/api/wigelltravels/v1/updatetravel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destination)))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void updateDestination_should_return_code_400_when_id_does_not_match_an_index() throws Exception {

        destination.setId(999L);

        mockMvc.perform(put("/api/wigelltravels/v1/updatetravel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(destination)))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void removeDestination_should_return_code_200_and_delete() throws Exception {

        destinationRepository.save(destination);

        mockMvc.perform(delete("/api/wigelltravels/v1/removetravel/" + destination.getId()))
                .andExpect(status().isOk());

        assertFalse(destinationRepository.existsById(destination.getId()));

    }

    @WithMockUser(username = "test user", roles = {"ADMIN"})
    @Test
    void removeDestination_should_return_code_400_when_id_not_found() throws Exception {

        mockMvc.perform(delete("/api/wigelltravels/v1/removetravel/999"))
                .andExpect(status().isBadRequest());
    }


}
