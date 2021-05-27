package gr.vacay.vacay.repository;

import gr.vacay.vacay.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    public List<City> findByName(String name);
}
