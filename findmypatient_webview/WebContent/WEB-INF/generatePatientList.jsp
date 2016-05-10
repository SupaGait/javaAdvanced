<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page import="java.util.List"%>
<%@ page import="fr.gklomphaar.findmypatient.datamodel.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div class="form" id="patientsForm">
	<c:choose>
		<c:when test="${not empty patientsList}">
			<table class="table table-hover table-bordered">
				<thead style="font-weight: bold;">
					<tr style="text-align: center;">
						<td>#</td>
						<td>First Name</td>
						<td>Last name</td>
						<td>Date of birth</td>
						<td>Room number</td>
						<td>Social security nr.</td>
						<td>Telephone nr.</td>
						<td>Email address</td>
						<td>Delete</td>
					</tr>
				</thead>
				<c:forEach var="patient" items="${patientsList}">
					<c:set var="classSucess" value="" />
					<%-- <c:if test ="${idEmployee == employee.id}">                           
                    <c:set var="classSucess" value="info"/>
                </c:if> --%>
					<tr class="${classSucess}" id="patient_${patient.id}_row">
						<td>${patient.id}</td>
						<td>${patient.firstName}</td>
						<td>${patient.lastName}</td>
						<td>${patient.dateOfBirth}</td>
						<td>${patient.roomNumber}</td>
						<td>${patient.socialSecurityNumber}</td>
						<td>${patient.telephoneNumber}</td>
						<td>${patient.email}</td>
						<td style="text-align: center;">				 
							<a id="deletePatient" onclick="callDeleteModal(${patient.id},'${patient.firstName}','${patient.lastName}')">
						  		<span class="glyphicon glyphicon-trash"/> <!-- trash or remove  -->
						  	</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">No patients found</div>
		</c:otherwise>
	</c:choose>
</div>