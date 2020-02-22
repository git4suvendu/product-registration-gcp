package com.product.registration.user.DAO;

import java.util.List;
import com.product.registration.user.model.*;

public interface UserRegistrationDAO {
	public abstract int createUsers (String UserEmail, String UserFirstName, String UserLastName, String UserMACaddr);	
	public abstract List<UserRecord> getAllUsers();
	public abstract int updateUser ( String UserFirstName, String UserLastName, String UserMACaddr);	
}
