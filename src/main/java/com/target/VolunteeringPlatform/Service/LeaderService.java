package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    public Map<User, Double> getTimeSpentByUser() {
        List<User> users = userRepository.findAll();
        Map<User, Double> userTimeSpent = new HashMap<>();
        for(User u : users) {

            System.out.println(u.getHours());
            userTimeSpent.put(u,u.getHours());
        }
        return userTimeSpent;
    }
}
