<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page import="java.util.List"%>
<%@ page import="fr.gklomphaar.findmypatient.datamodel.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<%
		// Retrieve the patientsList from the session and use
		List<Patient> patientList = (List<Patient>) session.getAttribute("patientsList");
		if(patientList == null) {
			System.out.println("patientList is empty...");
		}
		else {
			for(Patient patient : patientList) {
		%>
				<%= patient.getfName() + " " + patient.getlName() %>
				<br>
		<%	
			}
		}
		//${SessionScope.patientList}
		%>


    <%-- <h1>${list.firstName} ${student.lastName}</h1> --%>
</body>
</html>