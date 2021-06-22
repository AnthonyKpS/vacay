package gr.vacay.vacay.controller;

import gr.vacay.vacay.model.traveller.Traveller;
import gr.vacay.vacay.service.TravellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traveller")
public class TravellerController {

    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @GetMapping
    public ResponseEntity<List<Traveller>> getAllTravellers() {
        return new ResponseEntity<>(travellerService.findAllTravellers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Traveller> addTraveller(@RequestBody Traveller traveller) {
        return new ResponseEntity<>(travellerService.addTraveller(traveller), HttpStatus.CREATED);
    }
}
