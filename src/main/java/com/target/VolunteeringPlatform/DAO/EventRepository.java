package com.target.VolunteeringPlatform.DAO;

import com.target.VolunteeringPlatform.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    boolean existsByName(String name);
    Event findByName(String name);
}