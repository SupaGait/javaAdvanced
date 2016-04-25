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
		
		<!-- Message box  -->
		<div class="row">
			<div class="col-sm-5 col-sm-offset-2">
				<div id="messageBoxReturnStatus" class="alert"></div>
			</div>
		</div>
		
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

		<!-- Patient list  -->
		<h2>Patients</h2>
		<div action="/ListPatients" class="form" id="patientsForm">
			<c:choose>
				<c:when test="${not empty patientsList}">
					<table class="table table-hover table-bordered">
						<thead style="font-weight: bold;">
							<tr>
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
								<td>
									<a href="" id="deletePatient" onclick="sendDeleteInformation(${patient.id})">
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
<script type="text/javascript">
	function callbackForDelete(xhr) {
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('messageBoxReturnStatus')
		var jsonObj = JSON.parse(xhr.responseText);
		
		// Set the information of the container based on the message
		mssgContainer.innerHTML = jsonObj.message;
		if(jsonObj.succes == true) {
			mssgContainer.setAttribute("class","alert alert-success")
		}
		else {
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}

	function sendDeleteInformation(patientId) {
		data = {}
		data["patientId"] = patientId
		/* var formContainer = document.getElementById("form");
		var inputs = formContainer.getElementsByTagName("input");
		var inputsSize = inputs.length;
		var data = {};
		;
		for (var i = 0; i < inputsSize; i++) {
			data[inputs[i].name] = inputs[i].value;
		} */
		load("DeletePatient", data, callbackForDelete);
	}

	function load(url, data, callback) {
		var xhr;
		if (typeof XMLHttpRequest !== 'undefined')
			xhr = new XMLHttpRequest();
		else {
			var versions = [ "MSXML2.XmlHttp.5.0", "MSXML2.XmlHttp.4.0",
					"MSXML2.XmlHttp.3.0", "MSXML2.XmlHttp.2.0",
					"Microsoft.XmlHttp" ]
			for (var i = 0, len = versions.length; i < len; i++) {
				try {
					xhr = new ActiveXObject(versions[i]);
					break;
				} catch (e) {
				}
			} // end for
		}

		xhr.onreadystatechange = ensureReadiness;
		function ensureReadiness() {
			if (xhr.readyState < 4) {
				return;
			}

			if (xhr.status !== 200) {
				return;
			}

			// all is well  
			if (xhr.readyState === 4) {
				callback(xhr);
			}
		}

		xhr.open('POST', url, true);
		xhr.send(JSON.stringify(data));
	}
</script>

</html>