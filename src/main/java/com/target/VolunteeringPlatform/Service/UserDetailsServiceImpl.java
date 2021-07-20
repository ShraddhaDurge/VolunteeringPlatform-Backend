package com.target.VolunteeringPlatform.Service;


//import com.target.VolunteeringPlatform.DAO.RoleRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
//import com.target.VolunteeringPlatform.model.Role;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	UserRepository userRepository;

	public void saveUser(User user) {
		user.setActive(1);
		user.setRole("USER");
		userRepository.save(user);
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
