package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.RequestResponse.*;
import com.target.VolunteeringPlatform.Service.UserDetailsImpl;
import com.target.VolunteeringPlatform.Service.UserDetailsServiceImpl;
import com.target.VolunteeringPlatform.model.Profile;
import com.target.VolunteeringPlatform.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @CrossOrigin("http://localhost:3000")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody SignupRequest newUser) {

        if (userService.userSearchByEmail(newUser.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        User user = new User(newUser.getEmail(),newUser.getFirstname(),newUser.getLastname(), encoder.encode(newUser.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @CrossOrigin("http://localhost:3000")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> validateUser(@RequestBody LoginRequest loginRequest) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);

       UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

       List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

       return ResponseEntity.ok(new LoginResponse(userDetails.getId(),userDetails.getFirstname(), userDetails.getLastname(),
               userDetails.getEmail(), roles.get(0)));
    }

    //profile handling

    @CrossOrigin("http://localhost:3000")
    @RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
    public ResponseEntity<?> createProfile(@RequestBody ProfileRequest profileRequest){

        Profile profile = new Profile(profileRequest.getMobile_number(),profileRequest.getDob(), profileRequest.getAbout(),
                profileRequest.getGender(),profileRequest.getLocation(),profileRequest.getAddress());

        userService.saveProfile(profile,profileRequest.getEmail());

        return ResponseEntity.ok(new MessageResponse("Profile created successfully!"));
    }


    @CrossOrigin("http://localhost:3000")
    @GetMapping(value = "/getProfile/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable int userId){
        if (userService.findProfileByUserId(userId) == null ) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User Profile does not exist!"));
        }
        return ResponseEntity.ok(userService.findProfileByUserId(userId));
    }


}
