package com.GroupAssignment.WigellTravelsAPI.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@Entity
public class TravelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private BigDecimal totalPrice;

    @NonNull
    private String departureDate;

    @NonNull
    private int durationWeeks;

    @NonNull
    private String hotelName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@NonNull BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @NonNull
    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(@NonNull String departureDate) {
        this.departureDate = departureDate;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    @NonNull
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(@NonNull String hotelName) {
        this.hotelName = hotelName;
    }

    @NonNull
    public Destination getDestination() {
        return destination;
    }

    public void setDestination(@NonNull Destination destination) {
        this.destination = destination;
    }

    @NonNull
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(@NonNull Customer customer) {
        this.customer = customer;
    }

    @NonNull
    private Destination destination;

    @NonNull
    private Customer customer;
}
