package com.GroupAssignment.WigellTravelsAPI.repositories;

import com.GroupAssignment.WigellTravelsAPI.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long> {

    List<TravelBooking> findByCustomers_Username(String username);

    List<TravelBooking> findByCurrentlyActive(boolean currentlyActive);

    List<TravelBooking> findByDepartureDateLessThanEqual(Date date);

}
