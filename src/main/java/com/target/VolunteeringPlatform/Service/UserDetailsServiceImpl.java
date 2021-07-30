package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.DAO.ProfileRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.Profile;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	UserRepository userRepository;

	@Autowired
	ProfileRepository profileRepository;

	public void saveUser(User user) {
		user.setActive(1);
		user.setRole("USER");
		userRepository.save(user);
	}

	public User userSearchByEmail( String email) {
       return userRepository.findByEmail(email);
    }

	public void saveProfile(Profile profile,String email) {
		User user = userRepository.findByEmail(email);
		System.out.println(user);
		profile.setUser(user);
		profileRepository.save(profile);
	}
	public Profile findProfileByUserId(int userId) {
		List<Profile> allProfiles = profileRepository.findAll();
		Profile profile = new Profile();
		for(Profile p : allProfiles) {
			if(p.getUser().getId() == userId) {
				profile = p;
			}
		}
		return profile;
	}
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		User user = userRepository.findByEmail(email);
		System.out.println(user);
		if (user == null){
			throw new UsernameNotFoundException("Invalid username or password."+email);
		}
		return UserDetailsImpl.build(user);
	}

}
