package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class ListingDetails {
    private String id;
    private String description;
    private String street;
    private String suburb;
    private String country;
    private String pictureUrl;
    private Double price;
    private String amenities;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getSuburb() {
        return suburb;
    }
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAmenities() {
        return amenities;
    }
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public static ListingDetails docuToDetails(Document docu) {
        ListingDetails deets = new ListingDetails();

        deets.setId(docu.getString("_id"));
        deets.setDescription(docu.getString("description"));

        List<String> streetKeys = new ArrayList<>();
        streetKeys.add("address");
        streetKeys.add("street");

        deets.setStreet(docu.getEmbedded(streetKeys, String.class));

        List<String> suburbKeys = new ArrayList<>();
        suburbKeys.add("address");
        suburbKeys.add("suburb");

        deets.setSuburb(docu.getEmbedded(suburbKeys, String.class));

        List<String> countryKeys = new ArrayList<>();
        countryKeys.add("address");
        countryKeys.add("country");

        deets.setCountry(docu.getEmbedded(countryKeys, String.class));

        List<String> pictureUrlKeys = new ArrayList<>();
        pictureUrlKeys.add("images");
        pictureUrlKeys.add("picture_url");

        deets.setPictureUrl(docu.getEmbedded(pictureUrlKeys, String.class));

        deets.setPrice(docu.getDouble("price"));

        List<String> amenitiesList = docu.getList("amenities", String.class);

        deets.setAmenities(listToString(amenitiesList));

        return deets;
    }

    private static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (String item : list) {
            sb.append(item);
            sb.append(", ");
        }

        String combinedString = sb.toString();

        return combinedString.substring(0, combinedString.length() - 2);
    }
}
