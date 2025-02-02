package vttp2023.batch3.assessment.paf.bookings.models;

import static vttp2023.batch3.assessment.paf.bookings.repositories.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class SearchResult {
    private String id;
    private String address;
    private Double price;
    private String imageUrl;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SearchResult [id=" + id + ", address=" + address + ", price=" + price + ", imageUrl=" + imageUrl + "]";
    }

    public static SearchResult docuToSearchResult(Document docu) {
        
        SearchResult sr = new SearchResult();

        sr.setId(docu.getString(LID));

        List<String> addressStreetKeys = new ArrayList<>();
        addressStreetKeys.add("address");
        addressStreetKeys.add("street");
        
        sr.setAddress(docu.getEmbedded(addressStreetKeys, String.class));

        sr.setPrice(docu.getDouble(LPRICE));

        List<String> imagesUrlKeys = new ArrayList<>();
        imagesUrlKeys.add("images");
        imagesUrlKeys.add("picture_url");

        sr.setImageUrl(docu.getEmbedded(imagesUrlKeys, String.class));

        return sr;
    }
}
