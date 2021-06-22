package gr.vacay.vacay.repository;

import gr.vacay.vacay.model.city.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    City findByWikiPageId(int wikiPageId);
}
