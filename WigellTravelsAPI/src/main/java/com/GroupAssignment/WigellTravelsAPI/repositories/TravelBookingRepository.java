package com.GroupAssignment.WigellTravelsAPI.repositories;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long> {
}
