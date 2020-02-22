package com.product.registration.user.DAO;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.product.registration.user.model.UserRecord;


@Repository
public class UserRegistrationDAOImplementation implements UserRegistrationDAO {
	
	int StatusCode=-999;
	int insert_status_code_1 = -999;
	int insert_status_code_2 = -999;
	int final_insert_status_code = -999;
	
	private final String INSERT_SQL = "insert into user_registered (email_addr, first_name, last_name, mac_addr) values (?, ?, ?, ?)";
	//private final String INSERT_SQL_2 = "insert into user_login(login_id, password) values (?,?)";
	private final String SELECT_ALL_SQL = "select * from  user_registered" ;
	
	
	@Autowired JdbcTemplate jdbcTemplate;

	@Override
   public int createUsers(String UserEmail, String UserFirstName, String UserLastName, String UserMACaddr)  {
		insert_status_code_1 =   jdbcTemplate.update(INSERT_SQL,UserEmail, UserFirstName, UserLastName, UserMACaddr);
		//insert_status_code_2 = jdbcTemplate.update(INSERT_SQL_2,UserEmail, UserPassword);
		
		if ( insert_status_code_1 >= 1 )
			final_insert_status_code = 1;
			
		return final_insert_status_code;
		
	}

	@Override
	public List<UserRecord> getAllUsers()   {
		RowMapper<UserRecord> rowMapper = new UserRowMapper();
		   return this.jdbcTemplate.query(SELECT_ALL_SQL, rowMapper);
	}

	@Override
	public int updateUser(String UserId, String UserFirstName, String UserLastName) {
		if (!UserFirstName.isEmpty() && !UserLastName.isEmpty() &&  !UserId.isEmpty() )
		{
			String UPDATE_SQL = "update user_registered set first_name=?, last_name=? where login_id=?";
			StatusCode = jdbcTemplate.update(UPDATE_SQL, UserFirstName, UserLastName, UserId);
		}
		
		if (!UserFirstName.isEmpty() && UserLastName.isEmpty() &&  !UserId.isEmpty() )
		{
			String UPDATE_SQL = "update user_registered set first_name=? where login_id=?";
			StatusCode = jdbcTemplate.update(UPDATE_SQL, UserFirstName, UserId);
		}
		
		if (UserFirstName.isEmpty() && !UserLastName.isEmpty() &&  !UserId.isEmpty() )
		{
			String UPDATE_SQL = "update user_registered set last_name=? where login_id=?";
			StatusCode = jdbcTemplate.update(UPDATE_SQL, UserLastName, UserId);
		}
		
		return StatusCode;
	}

}
