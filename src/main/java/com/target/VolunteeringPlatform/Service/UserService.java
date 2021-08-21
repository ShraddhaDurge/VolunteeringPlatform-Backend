package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.DAO.ProfileRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.PayloadRequest.ProfileRequest;
import com.target.VolunteeringPlatform.model.Profile;
import com.target.VolunteeringPlatform.model.User;
import com.target.VolunteeringPlatform.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
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

    public User findUserById( int userId) {
		return userRepository.findById(userId);
	}

	public boolean existsById(int id) {
		return userRepository.existsById(id);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public Profile saveProfile(ProfileRequest profileRequest) {
		User user = userRepository.findByEmail(profileRequest.getEmail());
		System.out.println(user);;

        List<Profile> allProfiles = profileRepository.findAll();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = Date.valueOf(simpleDateFormat.format(profileRequest.getDob()));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		Date afterDate= new java.sql.Date(c.getTimeInMillis());
        Profile profile = null;

        try {
                for (Profile p : allProfiles) {
                    if (p.getUser() == user) {
						p.setAbout(profileRequest.getAbout());
						p.setAddress(profileRequest.getAddress());
						p.setGender(profileRequest.getGender());
						p.setLocation(profileRequest.getLocation());
						p.setMobile_number(profileRequest.getMobile_number());
						p.setDob(afterDate);
                        System.out.println("Profile updated successfully!");
                        profileRepository.save(p);
						profile = p;
                        break;
                    }
                }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        if(profile == null)
        {
            profile = new Profile(profileRequest.getMobile_number(), afterDate , profileRequest.getAbout(),
                    profileRequest.getGender(),profileRequest.getLocation(),profileRequest.getAddress());
            profile.setUser(user);
            profileRepository.save(profile);
        }
		System.out.println(afterDate);
        return profile;
	}

	public Profile findProfileByUserId(int userId) {
		List<Profile> allProfiles = profileRepository.findAll();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Profile profile = null;
		try {
			if(allProfiles == null) {
				profile = null;
			}
			else {
				for (Profile p : allProfiles) {
					if (p.getUser().getId() == userId) {
						profile = p;
                        Date date = Date.valueOf(simpleDateFormat.format(profile.getDob()));
                        profile.setDob(date);
                        System.out.println(profile.getDob());
						break;
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e);
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
