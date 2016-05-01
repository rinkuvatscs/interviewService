package com.interview.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.multipart.MultipartFile;

import com.interview.pojo.UserDetail;

public class UserDetailExtractor implements ResultSetExtractor<List<UserDetail>> {

	@Override
	public List<UserDetail> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<UserDetail> userDetailsList = new ArrayList<UserDetail>();
		UserDetail userDetail = null;
		while (rs.next()) {
			userDetail = new UserDetail();
			userDetail.setUserId(rs.getInt("userid"));
			userDetail.setFirstName(rs.getString("firstname"));
			userDetail.setLastName(rs.getString("lastname"));
			userDetail.setEmailAddress(rs.getString("email"));
			userDetail.setMobileNum(rs.getString("mobile"));
			userDetail.setStatus(rs.getString("status"));
			userDetail.setLocation(rs.getString("location"));
			userDetail.setTopic(rs.getString("topic"));
			if (rs.getBlob("file") != null) {
				MultipartFile file = (MultipartFile) rs.getBlob("file");
				userDetail.setFile(file);
			}

			userDetailsList.add(userDetail);
		}
		return userDetailsList;
	}

}
