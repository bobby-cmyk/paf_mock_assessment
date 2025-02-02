package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.ListingDetails;
import vttp2023.batch3.assessment.paf.bookings.models.SearchForm;
import vttp2023.batch3.assessment.paf.bookings.models.SearchResult;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
@RequestMapping
public class ListingsController {

	@Autowired
	private ListingsService listSvc;
	// Task 2
	@GetMapping
	public ModelAndView getSearchPage() {
		ModelAndView mav = new ModelAndView();

		SearchForm searchForm = new SearchForm();

		List<String> countries = listSvc.getCountries();

		mav.setViewName("search");
		mav.addObject("countries", countries);
		mav.addObject("searchForm", searchForm);

		return mav;
	}
	
	//Task 3
	@GetMapping("/search")
	public ModelAndView getSearchResults(
		@Valid SearchForm searchForm, BindingResult bindings) 
	{	
		ModelAndView mav = new ModelAndView();

		if (bindings.hasErrors()) {

			List<String> countries = listSvc.getCountries();

			mav.setViewName("search");
			mav.addObject("countries", countries);
			mav.addObject("searchForm", searchForm);

			return mav;
		}
		
		List<SearchResult> searchResults = listSvc.getSearchResults(searchForm);

		mav.setViewName("results");
		mav.addObject("searchForm", searchForm);
		mav.addObject("results", searchResults);

		return mav;
	}

	// Task 4
	@GetMapping("listing/{id}")
	public ModelAndView getListingById(@PathVariable(name="id") String id, @RequestParam MultiValueMap<String, String> searchParams) {

		ModelAndView mav = new ModelAndView();

		ListingDetails deets = listSvc.getListingById(id);

		Booking book = new Booking();
		book.setAccId(id);

		SearchForm searchForm = new SearchForm();
		searchForm.setCountry(searchParams.getFirst("country"));
		searchForm.setNumberOfPerson(Integer.parseInt(searchParams.getFirst("numberOfPerson")));
		searchForm.setMinPrice(Double.parseDouble(searchParams.getFirst("minPrice")));
		searchForm.setMaxPrice(Double.parseDouble(searchParams.getFirst("maxPrice")));

		mav.setViewName("listing");
		mav.addObject("deets", deets);
		mav.addObject("book", book);
		mav.addObject("searchForm", searchForm);

		return mav;
	}

	//Task 5
	@PostMapping("/book")
	public ModelAndView bookAccoms(@Valid Booking book, BindingResult bindings) {
		ModelAndView mav = new ModelAndView();

		if (bindings.hasErrors()) {

			System.out.printf("\n >>>> Acc ID: %s\n", book.getAccId());

			ListingDetails deets = listSvc.getListingById(book.getAccId());
			
			mav.setViewName("listing");
			mav.addObject("deets", deets);
			mav.addObject("book", book);
			mav.addObject(BindingResult.MODEL_KEY_PREFIX + "book", bindings);

			return mav;
		}

		try {
			
			String resvId = listSvc.bookAccoms(book);

			mav.setViewName("confirmation");
			mav.addObject("resvId", resvId);

			return mav;
		}

		catch(Exception e) {
			 // Retrieve listing details again to redisplay the form
			 ListingDetails deets = listSvc.getListingById(book.getAccId());
        
			 mav.setViewName("listing");
			 mav.addObject("deets", deets);
			 mav.addObject("book", book);
			 mav.addObject("errorMessage", "Accommodation is not available");

			return mav;
		}
		
	}

}
