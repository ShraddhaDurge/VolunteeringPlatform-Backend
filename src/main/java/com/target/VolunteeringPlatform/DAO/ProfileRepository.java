package com.target.VolunteeringPlatform.DAO;

import com.target.VolunteeringPlatform.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("profileRepository")
public interface ProfileRepository extends JpaRepository<Profile,Long>{

}