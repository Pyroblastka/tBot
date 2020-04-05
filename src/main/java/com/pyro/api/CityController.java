package com.pyro.api;

import com.pyro.entities.City;
import com.pyro.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {


    @Autowired
    CityRepository cityRepository;


    @GetMapping("/cities")
    List<City> all() {
        return cityRepository.findAll();
    }

    @PostMapping("/city")
    City newCity(@RequestParam String newCityName) {
        City city = new City(newCityName);
        return cityRepository.saveAndFlush(city);
    }

    @GetMapping("/city/{id}")
    City one(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {

        return cityRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @PatchMapping("/city/{id}")
    City updateCityName(@PathVariable Long id, @RequestParam String newName) {
        City city = cityRepository.findById(id).get();
        city.setName(newName);
        return cityRepository.saveAndFlush(city);
    }

    @DeleteMapping("/city/{id}")
    void deleteCity(@PathVariable Long id) {
        cityRepository.deleteById(id);
    }
}
