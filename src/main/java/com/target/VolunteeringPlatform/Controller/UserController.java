package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.PayloadRequest.LoginRequest;
import com.target.VolunteeringPlatform.PayloadRequest.ProfileRequest;
import com.target.VolunteeringPlatform.PayloadRequest.SignupRequest;
import com.target.VolunteeringPlatform.PayloadResponse.LoginResponse;
import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.UserService;
import com.target.VolunteeringPlatform.Service.UserDetailsImpl;
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

//Controller class for user specific functionalities
@RestController
@RequestMapping("/account")
@CrossOrigin("https://helping-hands-frontend.herokuapp.com")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    //Registration endpoint for new users to sign up
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody SignupRequest newUser) {

        //check if user email already exist in database
        if (userService.userExistsByEmail(newUser.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        User user = new User(newUser.getEmail(),newUser.getFirstname(),newUser.getLastname(), encoder.encode(newUser.getPassword()));
        user.setActive(1);
        user.setRole("USER");
        userService.saveUser(user);             //save new user details in database
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    //Validate and login existing user
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> validateUser(@RequestBody LoginRequest loginRequest) {
        //User authentication
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);

       //Get details of current logged-in user
       UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

       //get role of user
       List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

       return ResponseEntity.ok(new LoginResponse(userDetails.getId(),userDetails.getFirstname(), userDetails.getLastname(),
               userDetails.getEmail(), roles.get(0)));
    }

    //add profile details to already existing user
    @RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
    public ResponseEntity<?> createProfile(@RequestBody ProfileRequest profileRequest){

        System.out.println(userService.userExistsByEmail(profileRequest.getEmail()));
        //check if user email exists in database
        if (!userService.userExistsByEmail(profileRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User does not exists!"));
        }

        //Save profile details in system
        Profile profile = userService.saveProfile(profileRequest);
        System.out.println(profile);
        return ResponseEntity.ok(new MessageResponse("Profile created successfully!"));
    }

    //Get profile details associated with certain userId
    @GetMapping(value = "/getProfile/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable int userId){
        //check if user profile exists in database
        if (userService.findProfileByUserId(userId) == null ) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User Profile does not exist!"));
        }

        //return profile
        return ResponseEntity.ok(userService.findProfileByUserId(userId));
    }


}
