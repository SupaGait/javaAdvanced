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
						<td>Modify</td>
					</tr>
				</thead>
				<c:forEach var="patient" varStatus="loop" items="${patientsList}">
					<c:set var="classSucess" value="" />
					<tr class="${classSucess}" id="patient_${patient.id}_row">
						<td id="patient_id">${patient.id}</td>
						<td id="patient_firstName">${patient.firstName}</td>
						<td id="patient_lastName">${patient.lastName}</td>
						<td id="patient_dateOfBirth">${patient.dateOfBirth}</td>
						<td id="patient_roomNumber" >${patient.roomNumber}</td>
						<td id="patient_socialSecurityNumber">${patient.socialSecurityNumber}</td>
						<td id="patient_telephoneNumber">${patient.telephoneNumber}</td>
						<td id="patient_email">${patient.email}</td>
						<td style="text-align: center;">
						<div style="width:40px;">
							<div style="float:left; margin-left: 5px;" >
								<a id="deletePatient" onclick="callDeleteModal(${patient.id},'${patient.firstName}','${patient.lastName}')">
							  		<span class="glyphicon glyphicon-trash"/>
							  	</a>
						  	</div>
						  	<div style="float:left; margin-left: 5px;">
							  	<a id="modifyPatient" onclick="callModifyModal(	${patient.id},
																			  	'${patient.firstName}',
																			  	'${patient.lastName}',
																			  	'${patient.dateOfBirth}',
																			  	'${patient.roomNumber}',
																			  	'${patient.socialSecurityNumber}',
																			  	'${patient.telephoneNumber}',
																			  	'${patient.email}')">
							  		<span class="glyphicon glyphicon-wrench"/>
							  	</a>
						  	</div>
						</div>
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