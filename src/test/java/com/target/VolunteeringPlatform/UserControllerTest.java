package com.target.VolunteeringPlatform;

import com.target.VolunteeringPlatform.Controller.UserController;
import com.target.VolunteeringPlatform.PayloadRequest.ProfileRequest;
import com.target.VolunteeringPlatform.PayloadRequest.SignupRequest;
import com.target.VolunteeringPlatform.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    Authentication authentication;

    @Mock
    PasswordEncoder encoder;

    @BeforeEach
    public void setupMock() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserTest_emailAlreadyTaken(){
        SignupRequest newUser = new SignupRequest("firstname","lastname","demo@gmail.com","abc@123");
        String expectedResponse = "MessageResponse{message='Error: Email is already taken!'}";
        when(userService.userExistsByEmail(anyString())).thenReturn(true);
        ResponseEntity<?> response = userController.createUser(newUser);
        assertEquals(expectedResponse,response.getBody().toString());
    }

    @Test
    void createUserTest_emailIsNotTaken(){
        SignupRequest newUser = new SignupRequest("firstname","lastname","demo@gmail.com","abc@123");
        String expectedResponse = "MessageResponse{message='User registered successfully!'}";
        doNothing().when(userService).saveUser(any());
        ResponseEntity<?> response = userController.createUser(newUser);
        assertEquals(expectedResponse,response.getBody().toString());
    }

    @Test
    void createProfileTest_EmailExists(){
        ProfileRequest userProfile = new ProfileRequest("demo@gmail.com","9785776166",new Date(1999, 11, 10),"Student","Female","Pune","Pune");
        String expectedResponse = "MessageResponse{message='Profile created successfully!'}";
        when(userService.userExistsByEmail(anyString())).thenReturn(true);
        ResponseEntity<?> response = userController.createProfile(userProfile);
        assertEquals(expectedResponse,response.getBody().toString());
    }

    @Test
    void createProfileTest_emailNotExist(){
        ProfileRequest userProfile = new ProfileRequest("demo@gmail.com","9785776166",new Date(1999, 11, 10),"Student","Female","Pune","Pune");
        String expectedResponse = "MessageResponse{message='Error: User does not exists!'}";
        when(userService.userExistsByEmail(anyString())).thenReturn(false);
        ResponseEntity<?> response = userController.createProfile(userProfile);
        assertEquals(expectedResponse,response.getBody().toString());
    }

//    @Test
//    void validateUserTest(){
//        LoginRequest newUser = new LoginRequest("demo@gmail.com","abc@123");
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(new SimpleGrantedAuthority("USER"));
//        UserDetailsImpl userDetails = new UserDetailsImpl(1,"firstname","lastname","demo@gmail.com","abc@123",roles);
//
//        String expectedResponse = "MessageResponse{message='User registered successfully!'}";
//
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        ResponseEntity<?> response = userController.validateUser(newUser);
//        assertEquals(expectedResponse,response.getBody().toString());
//    }



}
