package gr.vacay.vacay.service;

import gr.vacay.vacay.model.City;
import gr.vacay.vacay.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity (City city) {
        return cityRepository.save(city);
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }
}
