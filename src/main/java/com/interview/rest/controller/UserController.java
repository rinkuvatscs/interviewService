package com.interview.rest.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.mysqlDb.UserDetailService;
import com.interview.pojo.User_Details;

@RestController
public class UserController {

	@Autowired
	private UserDetailService userDetailService;

	@RequestMapping(value = "/addUser", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<User_Details> addUserDetails(@RequestBody User_Details user_Details)
			throws SQLException {
		User_Details userDetails = null;
		userDetails = userDetailService.addUser(user_Details);
		return new ResponseEntity<User_Details>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<Integer, User_Details>> getUserDetails() throws SQLException {
		Map<Integer, User_Details> userDetails = userDetailService.getUserDetails();
		return new ResponseEntity<Map<Integer, User_Details>>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/modifyByEmail", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<User_Details> modifyByEmail(@RequestBody User_Details user_Details)
			throws SQLException {
		User_Details userDetails = null;
		userDetails = userDetailService.modifyByEmail(user_Details);
		return new ResponseEntity<User_Details>(userDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/activateDeactivateUser", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> activateDeactivateUser(@RequestParam String email, String status)
			throws SQLException {
		String userDetails = userDetailService.activateDeactivateUser(email, status);
		return new ResponseEntity<String>(userDetails, HttpStatus.OK);
	}
}