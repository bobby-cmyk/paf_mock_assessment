package vttp2023.batch3.assessment.paf.bookings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@SpringBootApplication
public class BookingsApplication implements CommandLineRunner{

	@Autowired
	private ListingsRepository listRepo;
	public static void main(String[] args) {
		SpringApplication.run(BookingsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<String> countries = listRepo.getCountries();
		
		for (String country : countries) {
			System.out.println(country);
		}
	}

}
