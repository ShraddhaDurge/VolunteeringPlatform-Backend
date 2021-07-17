package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.RequestResponse.LoginRequest;
import com.target.VolunteeringPlatform.RequestResponse.LoginResponse;
import com.target.VolunteeringPlatform.RequestResponse.MessageResponse;
import com.target.VolunteeringPlatform.RequestResponse.SignupRequest;
import com.target.VolunteeringPlatform.Service.UserDetailsImpl;
import com.target.VolunteeringPlatform.Service.UserDetailsServiceImpl;
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

        if (userRepository.findByEmail(newUser.getEmail()) != null) {
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

        return ResponseEntity.ok(new LoginResponse(userDetails.getId(),
                userDetails.getEmail(), roles.get(0)));
    }
}
