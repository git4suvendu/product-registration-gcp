package com.product.registration.user;

// New Code for Deb Da

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.product.registration.user.DAO.UserRegistrationDAOImplementation;
import com.product.registration.user.exception.UserRegistrationGenericException;
import com.product.registration.user.model.UserRecord;

 
 

@RestController 
public class UserRegistrationController {


	@Autowired RestTemplate restTemplate;
	@Autowired  UserRegistrationDAOImplementation userRegDAO;
	
	   @RequestMapping(value = "/")
	   public ResponseEntity<Object> default_response() {	
		   return new ResponseEntity<>(200,HttpStatus.OK);		   
	   }

	
	  @RequestMapping(value = "/user-reg/create/CreateUser", headers="Content-Type=application/json", method = RequestMethod.POST)
	   public ResponseEntity<Object> createUser(@RequestBody UserRecord userRecBody ) {	
		   
		   String UserEmail = userRecBody.getUseremail();
		   String MACaddr = userRecBody.getMacaddr();
		   String FirstName = userRecBody.getFirstname();
		   String LastName = userRecBody.getLastname();

		   
		   if(isNullOrEmpty(UserEmail)) return new ResponseEntity<>("User Email cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(FirstName)) return new ResponseEntity<>("User First Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(LastName)) return new ResponseEntity<>("User Last Name cannot be blank.", HttpStatus.BAD_REQUEST); 
		   if(isNullOrEmpty(MACaddr)) return new ResponseEntity<>("Cannot fetch the MAC address", HttpStatus.BAD_REQUEST); 
		   


//		    boolean UserIdExists = restTemplate.getForObject("http://USER-SEARCH-DELETE-SERVICE/SearchUser/"+ UserId, boolean.class );
		    //boolean   UserIdExists = false;
			  
//		   if (UserIdExists)    return new ResponseEntity<>("User already exists.", HttpStatus.CONFLICT); 
		   

		
		  if(userRegDAO.createUsers(UserEmail, FirstName, LastName, MACaddr) >= 1){
		  return new ResponseEntity<>("User has been created successfully",
		  HttpStatus.CREATED); }else{ throw new UserRegistrationGenericException(); }
		 
	   }
	  
	  
		// REST API Calling Method: GET
		// http://localhost:8080/GetAllUsers
	   
	   @RequestMapping(value = "/GetAllUsers", method = RequestMethod.GET)
	   public ResponseEntity<Object> fetchAllUsers() {	
		   return new ResponseEntity<>(userRegDAO.getAllUsers(), HttpStatus.OK);		   
	   }

	  
	   public static boolean isNullOrEmpty(String str) {
	        if(str != null && !str.isEmpty())
	            return false;
	        return true;
	    }
	   
	
}
