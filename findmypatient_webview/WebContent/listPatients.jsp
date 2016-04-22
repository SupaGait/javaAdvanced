<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page import="java.util.List"%>
<%@ page import="fr.gklomphaar.findmypatient.datamodel.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Patient overview</title>
	
	<!-- Style sheets, Bootstrap & custom -->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="generalLayout.css" rel="stylesheet">
</head>
<body>
	<%@ include file="generalMenu.jsp"%>

	<div class="container">
		
		<!--Search Form -->
		<form action="/ListPatients" method="get" id="seachPatientForm">
			<input type="hidden" id="searchAction" name="searchAction" value="searchByName" />
			<div class="form-group col-xs-offset-7 col-xs-4">
				<input type="text" name="patientName" id="patientName" class="form-control" />
			</div>
			<div class="form-group col-xs-1">
				<button type="submit" class="btn btn-info">
					<span class="glyphicon glyphicon-search"></span> Search
				</button>
			</div>
			<br>
		</form>

		<h2>Patients</h2>
		<form action="/ListPatients" method="post" id="patientsForm">
			<c:choose>
				<c:when test="${not empty patientsList}">
					<table class="table table-hover">
						<thead>
							<tr>
								<td>#</td>
								<td>First Name</td>
								<td>Last name</td>
								<td>Date of birth</td>
								<td>Room number</td>
								<td>Social security nr.</td>
								<td>Telephone nr.</td>
								<td>Email address</td>
							</tr>
						</thead>
						<c:forEach var="patient" items="${patientsList}">
							<c:set var="classSucess" value="" />
							<%-- <c:if test ="${idEmployee == employee.id}">                           
		                    <c:set var="classSucess" value="info"/>
		                </c:if> --%>
							<tr class="${classSucess}">
								<td>${patient.id}</td>
								<td>${patient.firstName}</td>
								<td>${patient.lastName}</td>
								<td>${patient.dateOfBirth}</td>
								<td>${patient.roomNumber}</td>
								<td>${patient.socialSecurityNumber}</td>
								<td>${patient.telephoneNumber}</td>
								<td>${patient.email}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					</br>
					<div class="alert alert-info">No patients found</div>
				</c:otherwise>
			</c:choose>
		</form>

		<div>
			<a href="CreatePatient">
				<button type="submit" class="btn btn-primary  btn-md">New Patient</button>
			</a>
		</div>

	</div>	<!-- container end  -->
</body>
<!-- Bootstrap scripts -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
</html>