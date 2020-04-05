package com.pyro.repositories;

import com.pyro.entities.City;
import com.pyro.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByNameContainsIgnoreCase(String name);
    City findByMessagesContains(Message id);
}
