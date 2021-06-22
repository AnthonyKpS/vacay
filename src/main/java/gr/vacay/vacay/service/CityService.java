package gr.vacay.vacay.service;

import gr.vacay.vacay.exception.DuplicateCityPageIdException;
import gr.vacay.vacay.model.city.City;
import gr.vacay.vacay.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(City city) {

        // Check for duplicates by pageId
        try {
            city.augmentCity();
            System.out.println(city);
            return cityRepository.save(city);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateCityPageIdException();
        }
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }
}
