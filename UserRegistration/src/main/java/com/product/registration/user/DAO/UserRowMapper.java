package com.product.registration.user.DAO;

import org.springframework.jdbc.core.RowMapper; 
import com.product.registration.user.model.*;

import java.sql.ResultSet;
import java.sql.SQLException; 

public class UserRowMapper implements RowMapper<UserRecord> {
	@Override
	   public UserRecord mapRow(ResultSet row, int rowNum) throws SQLException {
		UserRecord UserRec = new UserRecord();
		UserRec.setUseremail(row.getString("EMAIL_ADDR"));
		UserRec.setMacaddr(row.getString("MAC_ADDR"));
		UserRec.setFirstname(row.getString("FIRST_NAME"));
		UserRec.setLastname(row.getString("LAST_NAME"));
		return UserRec;
	   }
}
