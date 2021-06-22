package gr.vacay.vacay.repository;

import gr.vacay.vacay.model.traveller.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravellerRepository extends JpaRepository<Traveller, Long> {
}
