package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.models.SearchForm;

import static vttp2023.batch3.assessment.paf.bookings.repositories.Constants.*;

@Repository
public class ListingsRepository {

	@Autowired
	private JdbcTemplate sqlTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	// Task 2
	// db.listings.distinct('address.country')
	public List<String> getCountries() {
		
		List<String> countries = mongoTemplate.findDistinct(new Query(), LCOUNTRY, "listings", String.class);
		
		return countries;
	}
	
	// Task 3
	/*
	 	db.listings.find({
			"address.country": {
				$regex: "Australia", 
				$options: "i"
			},
			accommodates: NumberInt(2),
			price: {
				$gte: 1.0, 
				$lte: 500.0
			}
		})
		.sort({price: -1})
		.projection({'address.street':1, price:1, 'images.picture_url':1})
	 */
	public List<Document> getSearchResults(SearchForm searchForm) {
		
		Criteria criteria = Criteria
			.where(LCOUNTRY).regex(searchForm.getCountry(), "i")
			.and(LACCOMMODATES).is(searchForm.getNumberOfPerson())
			.and(LPRICE).gte(searchForm.getMinPrice()).lte(searchForm.getMaxPrice());

		Query query = Query.query(criteria)
			.with(Sort.by(Sort.Direction.DESC, LPRICE));
		
		query.fields()
			.include(LSTREET, LPRICE, LPICTUREURL);

		List<Document> results = mongoTemplate.find(query, Document.class, COLLECTION_L);

		return results;
	}


	//Task 4
	/*
	 	db.listings.find({
			_id : "16134812"
		})
		.projection({
			'description':1,
			'address.street':1,
			'address.suburb':1,
			'address.country':1,
			'images.picture_url':1,
			'price':1,
			'amenities':1})
	 */
	public Document getListingById(String id) {
		
		Criteria criteria = Criteria
			.where(LID).is(id);

		Query query = Query.query(criteria);

		query.fields()
			.include(LDESCRIPTION, LSTREET, LSUBURB, LCOUNTRY, LPICTUREURL, LPRICE, LAMENITIES);
		
		Document result = mongoTemplate.find(query, Document.class, COLLECTION_L).get(0);

		return result;
	}

	//Task 5
	
	public Integer getAccVacancy(String accId) {

		// Row locking
		SqlRowSet rs = sqlTemplate.queryForRowSet(
			"""
			SELECT vacancy FROM acc_occupancy WHERE acc_id = ? FOR UPDATE		
			""",
			accId
		);

		rs.next();

		Integer vacancy = rs.getInt("vacancy");

		return vacancy;
	}

	public boolean addBooking(Booking book) {

		int bookAdded = sqlTemplate.update(
			"""
			INSERT INTO reservations (resv_id, name, email, acc_id, arrival_date, duration)	
				VALUES (?, ?, ?, ?, ?, ?)
			""",
			book.getResvId(),
			book.getName(),
			book.getEmail(),
			book.getAccId(),
			book.getArrivalDate(),
			book.getDuration()
		);

 		return bookAdded > 0;
	}

	public boolean deductVacancy(String accId, Integer updatedVacancy) {
		int vacancyUpdated = sqlTemplate.update(
			"""
			UPDATE acc_occupancy
			SET vacancy	= ?
			WHERE acc_id = ?
			""",
			updatedVacancy,
			accId
			);

		return vacancyUpdated > 0;
	} 

}
