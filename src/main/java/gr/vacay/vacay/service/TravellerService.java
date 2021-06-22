package gr.vacay.vacay.service;

import gr.vacay.vacay.model.traveller.Traveller;
import gr.vacay.vacay.model.city.City;
import gr.vacay.vacay.repository.CityRepository;
import gr.vacay.vacay.repository.TravellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravellerService {

    private final TravellerRepository travellerRepository;
    private final CityRepository cityRepository;

    @Autowired
    public TravellerService(TravellerRepository travellerRepository, CityRepository cityRepository) {
        this.travellerRepository = travellerRepository;
        this.cityRepository = cityRepository;
    }

    public Traveller addTraveller(Traveller traveller) {

        // Isolate the Cities for this Traveller
        List<City> cities = traveller.getCities();

        int index = 0;
        for (City city :
                cities) {
            City found = cityRepository.findByWikiPageId(city.getWikiPageId());
            if (found == null) {
                city.augmentCity();
                cityRepository.save(city);
            } else {
                cities.set(index, found);
            }
            index++;
        }

        // Find the matching city
        City matchingCity = traveller.findMatchingCity();

        // Update the matching city id
        traveller.setMatchingCityWikiPageId(matchingCity.getWikiPageId());

        // Save the traveller
        travellerRepository.save(traveller);

        return traveller;
    }

    public List<Traveller> findAllTravellers() {
        return travellerRepository.findAll();
    }
}
