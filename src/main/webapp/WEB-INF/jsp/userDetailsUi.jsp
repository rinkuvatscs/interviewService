<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.interview.pojo.Topic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/interviewservice/topicservice/addTopic" method="post">
		<table>
			<tr>
				<td><label>Enter your First Name : </label></td>
				<td><input type="text" name="fName" /></td>
			</tr>
			<tr>
				<td><label>Enter your Last Name : </label></td>
				<td><input type="text" name="lName" /></td>
			</tr>
			<tr>
				<td><label>Enter your Mobile Number : </label></td>
				<td><input type="text" name="mNo" /></td>
			</tr>
			<tr>
				<td><label>Enter your Email : </label></td>
				<td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<td><label>Enter your Location : </label></td>
				<td><input type="text" name="location" /></td>
			</tr>
			<tr>
				<td><label>Enter your Topic Name : </label></td>
				<td><select name="tName">
						<%
							Object obj = request.getAttribute("msgList");
							if (obj instanceof List<?>) {
								List<Topic> topicList = (ArrayList<Topic>) obj;
								for (int i = 0; i < topicList.size(); i++) {
									out.print(topicList.get(i).getTopicName());
						%>
						<option value=<%=topicList.get(i).getTopicId()%>>
							<%=topicList.get(i).getTopicName()%>
						</option>
						<%
							}
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="submit" value="Submit" /></td>
			</tr>
		</table>



	</form>
</body>
</html>