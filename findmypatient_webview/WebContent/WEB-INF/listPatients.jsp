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
				<div id="messageBoxReturnStatus" class="alert invisible">Message</div>
			</div>
		</div>
		
		<!--Search Form -->
		<div class="row">
  			<div class="col-sm-6 col-sm-offset-6">
				<div class="input-group">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Search by <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<!-- /btn-group -->
					<input type="text" class="form-control" aria-label="...">
				</div>
			</div>
		</div>
		<!-- /input-group -->
		<form action="/ListPatients" method="get" id="seachPatientForm">
			<!-- <input type="hidden" id="searchAction" name="searchAction" value="searchByName" /> -->
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
									<%-- <a id="deletePatient" onclick="sendDeleteInformation(${patient.id})"> --%> 
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
		
		<!-- Delete confirm modal window  -->
		<div class="modal fade" id="delConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Are you sure?</h4>
					</div>
					<div class="modal-body" id="delConfirmModalBody">
						<div style="font-weight: bold;">Deleting patient:</div>
						<div style="font-size: 20px;" id="delConfirmModalField"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button id="deleteConfirmedButton" type="button" class="btn btn-warning" data-dismiss="modal" onclick="">Delete</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12" style="text-align: right">
				<nav>
				  <ul class="pagination">
				    <li>
				      <a href="#" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				    <li><a href="#">1</a></li>
				    <li><a href="#">2</a></li>
				    <li><a href="#">3</a></li>
				    <li><a href="#">4</a></li>
				    <li><a href="#">5</a></li>
				    <li>
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				  </ul>
				</nav>
			</div>
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

	// Set modal info on show
	var callDeleteModal = (function (patientId, patientFrontName, patientLastName) {
	    var deModalElement = document.getElementById('delConfirmModal');
	    var deModalElementButton = document.getElementById('deleteConfirmedButton');
	    var delConfirmModalFieldElement = document.getElementById('delConfirmModalField');

	    // Set fields, show window
	    return function (patientId, patientFrontName, patientLastName) {
	    	deModalElementButton.onclick = new Function("sendDeleteInformation("+patientId+")");
	    	delConfirmModalFieldElement.innerHTML = patientFrontName + " " + patientLastName + " (id: "+ patientId + ")";
	    	$("#delConfirmModal").modal();
	    	return;
	    	}
	})();

	function callbackForDelete(xhr, patientId) {
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('messageBoxReturnStatus') 
		var patientRowElement = document.getElementById("patient_"+patientId+"_row");
		
		// Set the information of the container based on the message
		var jsonObj = JSON.parse(xhr.responseText);
		mssgContainer.innerHTML = jsonObj.message;
		if(jsonObj.succes == true) {
			// Remove the row containing the patientId
			mssgContainer.setAttribute("class","alert alert-success")
			patientRowElement.parentNode.removeChild(patientRowElement);
		}
		else {
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}

	function sendDeleteInformation(patientId) {
		data = {}
		data["patientId"] = patientId;
		load("DeletePatient", data, patientId, callbackForDelete);
	}

	function load(url, data, patientId, callback) {
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
				callback(xhr, patientId);
			}
		}

		xhr.open('POST', url, true);
		xhr.send(JSON.stringify(data));
	}
</script>

</html>