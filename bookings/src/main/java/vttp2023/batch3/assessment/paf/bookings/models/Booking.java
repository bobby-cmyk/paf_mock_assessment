package vttp2023.batch3.assessment.paf.bookings.models;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Booking {
    private String resvId;

    @NotBlank(message="Name cannot be empty")
    private String name;

    @NotBlank(message="Email cannot be empty")
    private String email;

    private String accId;

    @NotNull(message="Arrival Date cannot be empty")
    @FutureOrPresent(message="Arrival Date must be today or future")
    private LocalDate arrivalDate;

    @NotNull(message="Stay duration cannot be empty")
    @Min(value=1, message="Minimum of 1 day stay")
    private Integer duration;
    
    public String getResvId() {
        return resvId;
    }
    public void setResvId(String resvId) {
        this.resvId = resvId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAccId() {
        return accId;
    }
    public void setAccId(String accId) {
        this.accId = accId;
    }
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }
    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
