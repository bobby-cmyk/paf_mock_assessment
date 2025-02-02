package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.ListingDetails;
import vttp2023.batch3.assessment.paf.bookings.models.SearchForm;
import vttp2023.batch3.assessment.paf.bookings.models.SearchResult;
import static vttp2023.batch3.assessment.paf.bookings.models.SearchResult.*;
import static vttp2023.batch3.assessment.paf.bookings.models.ListingDetails.*;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {
	
	@Autowired
	private ListingsRepository listRepo;
	// Task 2
	public List<String> getCountries() {
		return listRepo.getCountries();
	}
	
	//Task 3
	public List<SearchResult> getSearchResults(SearchForm searchForm) {
		
		List<SearchResult> searchResults = new ArrayList<>();

		for (Document docu : listRepo.getSearchResults(searchForm)) {

			SearchResult result = docuToSearchResult(docu);

			System.out.println(result);

			searchResults.add(result);
		}

		return searchResults;
	}

	// Task 4
	public ListingDetails getListingById(String id) {

		Document docu = listRepo.getListingById(id);

		return docuToDetails(docu);
	}

	//Task 5
	@Transactional
	public String bookAccoms(Booking book) throws Exception{
		
		Integer vacancy = listRepo.getAccVacancy(book.getAccId());
		Integer stayDuration = book.getDuration();

		// If duration is more than vacancy
		if (!isAvailable(vacancy, stayDuration)) {
			throw new IllegalStateException("Not enough vacancy");
		}

		String resv_id = UUID.randomUUID().toString().substring(0, 8);
		book.setResvId(resv_id);

		String accId = book.getAccId();
		Integer updatedVacancy = vacancy - stayDuration;

		if (!listRepo.addBooking(book) || !listRepo.deductVacancy(accId, updatedVacancy)) {
			throw new IllegalStateException("Error adding booking and updating vacancy");
		}

		return resv_id;
	}

	private Boolean isAvailable(Integer vacancy, Integer stayDuration) {

		return stayDuration <= vacancy;
	}
}
