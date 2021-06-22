package gr.vacay.vacay.controller;

import gr.vacay.vacay.model.city.City;
import gr.vacay.vacay.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return new ResponseEntity<>(cityService.findAllCities(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {
        return new ResponseEntity<>(cityService.addCity(city), HttpStatus.CREATED);
    }
}
