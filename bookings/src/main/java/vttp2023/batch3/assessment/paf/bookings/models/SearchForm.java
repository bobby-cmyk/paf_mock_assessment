package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SearchForm {
    @NotNull(message="Country cannot be empty")
    @NotEmpty(message="Country cannot be empty")
    private String country;

    @NotNull(message="Number of person cannot be empty")
    @Min(value=1, message="Number of person cannot be less than 1")
    @Max(value=10, message="Number of person canot be more than 10 ")
    private Integer numberOfPerson;

    @NotNull(message="Minimum price cannot be empty")
    @Min(value=1, message="Minimum price cannot be less than 1.0")
    private Double minPrice;

    @NotNull(message="Maximum price cannot be empty")
    @Max(value=10000, message="Maximum price cannot be more than 10000")
    private Double maxPrice;
    
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getNumberOfPerson() {
        return numberOfPerson;
    }
    public void setNumberOfPerson(Integer numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }
    public Double getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
    public Double getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
