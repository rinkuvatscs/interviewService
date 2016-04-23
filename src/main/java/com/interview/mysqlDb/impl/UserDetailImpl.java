package com.interview.mysqlDb.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.interview.constants.Query;
import com.interview.extractor.UserDetailsExtractor;
import com.interview.mysqlDb.UserDetailService;
import com.interview.pojo.User_Details;

@Component
public class UserDetailImpl implements UserDetailService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User_Details addUser(User_Details user_Details) throws SQLException {

		User_Details doExist = isUserExist(user_Details);
		if (doExist != null && doExist.getUser_id() > 0) {
			return doExist;
		}
		String sql = Query.addUserDetails;
		List<String> args = new ArrayList<>();
		args.add(user_Details.getFirst_name());
		args.add(user_Details.getLast_name());
		args.add(user_Details.getEmail());
		if (user_Details.getMobile_number() > 0) {
			args.add(String.valueOf(user_Details.getMobile_number()));
		} else {
			args.add("0");
		}

		try {
			int response = jdbcTemplate.update(sql, args.toArray());
			if (response != 0) {
				User_Details details = getUserByEmail(user_Details.getEmail());
				return details;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Map<Integer, User_Details> getUserDetails() throws SQLException {

		String sql = Query.getUserDetails;
		try {
			List<User_Details> response = jdbcTemplate.query(sql, new UserDetailsExtractor());
			if (response != null && !response.isEmpty()) {
				Map<Integer, User_Details> map = new HashMap<>();
				for (int i = 0; i < response.size(); i++) {
					map.put(response.get(i).getUser_id(), response.get(i));
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User_Details getUserByEmail(String name) throws SQLException {

		String sql = Query.getUserByEmail;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(name)) {
			str.append(" email = ? ");
			args.add(name);
		}
		try {
			List<User_Details> response = jdbcTemplate.query(sql + str, args.toArray(), new UserDetailsExtractor());
			if (response != null && !response.isEmpty()) {
				return response.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User_Details modifyByEmail(User_Details user_Details) throws SQLException {

		StringBuffer str = new StringBuffer(" UPDATE USER_DETAILS ");
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(user_Details)) {
			if (!StringUtils.isEmpty(user_Details.getEmail())) {
				if (!StringUtils.isEmpty(user_Details.getFirst_name())) {
					str.append("SET FIRST_NAME = ? ");
					args.add(user_Details.getFirst_name());
				}
				if (!StringUtils.isEmpty(user_Details.getLast_name())) {
					str.append(", LAST_NAME = ? ");
					args.add(user_Details.getLast_name());
				}
				if (user_Details.getMobile_number() > 0) {
					str.append(", MOBILE_NUMBER = ? ");
					args.add(String.valueOf(user_Details.getMobile_number()));
				}
				if (!StringUtils.isEmpty(user_Details.getStatus())) {
					str.append(", SET STATUS = ? ");
					args.add(user_Details.getStatus());
				}
				str.append("WHERE EMAIL = ? ");
				args.add(user_Details.getEmail());

				try {
					int response = jdbcTemplate.update(str.toString(), args.toArray());
					if (response > 0) {
						User_Details user = getUserByEmail(user_Details.getEmail());
						return user;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public String activateDeactivateUser(String email, String status) throws SQLException {

		String sqlDeactive = Query.deactivateUser;
		String sqlActive = Query.activateUser;
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(email)) {
			args.add(email);
			if (StringUtils.isEmpty(status) || status.equalsIgnoreCase("D")) {
				try {
					int response = jdbcTemplate.update(sqlDeactive, args.toArray());
					if (response > 0) {
						return "User successfully Deactivat...";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					int response = jdbcTemplate.update(sqlActive, args.toArray());
					if (response > 0) {
						return "User successfully Active Again...";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private User_Details isUserExist(User_Details user_Details) {

		String sql = Query.getUserByEmail;
		StringBuffer str = new StringBuffer();
		List<String> args = new ArrayList<>();
		if (!StringUtils.isEmpty(user_Details.getEmail())) {
			str.append(" email = ? ");
			args.add(user_Details.getEmail());
			if (user_Details.getMobile_number() > 0) {
				str.append(" OR mobile_number = ? ");
				args.add(String.valueOf(user_Details.getMobile_number()));
			}
		}
		try {
			List<User_Details> response = jdbcTemplate.query(sql + str, args.toArray(), new UserDetailsExtractor());
			if (response != null && !response.isEmpty()) {
				User_Details details = response.get(0);
				return details;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}