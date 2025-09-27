package com.GroupAssignment.WigellTravelsAPI.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @ManyToMany
    @JoinTable(
            name = "booking_customer",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers = new ArrayList<>();


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
    public List<Customer> getCustomers() {
        return customers;
    }

    @NonNull
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
