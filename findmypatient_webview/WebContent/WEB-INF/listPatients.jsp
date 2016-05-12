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
	<link href="css/generalLayout.css" rel="stylesheet">
</head>
<body>
	<%@ include file="generalMenu.jsp"%>

	<div class="container">
		
		<!-- Message box  -->
		<div class="row">
			<div class="col-sm-5 col-sm-offset-2">
				<div id="alertMessageBox" class="alert invisible">Message</div>
			</div>
		</div>
		
		<!--Search Form -->
		<div class="row">
  			<div class="col-sm-10">
				<div class="input-group">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Search patients 
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-menu-right">
							<!-- Populate the possible search options -->
							<div id="searchFieldsContainer">
								<c:choose>
									<c:when test="${not empty searchFieldList}">
										<c:forEach var="searchField" items="${searchFieldList}">
											<li>
										    	<div class="input-group input-group-sm">
											      	<span class="input-group-addon">${searchField}</span>
											      	<input type="text" class="form-control" name="${searchField}" placeholder="${searchField} of the patient"/>
											   </div>
											</li>
										</c:forEach>
										<li>
											<button onclick="sendSearchPatient()" type="submit" class="btn btn-primary">Search</button>
										</li>
									</c:when>
									<c:otherwise>
										<li>No search possible</li>
									</c:otherwise>
								</c:choose>
							</div>
						</ul>
					</div>
				</div>
			</div>
			<div  class="col-sm-1">
				<a href="CreatePatient">
					<button type="submit" class="btn btn-primary  btn-md">New Patient</button>
				</a>
		</div>
		</div>

		<!-- Patient list  -->
		<h2>Patients</h2>
		<div id="patientListContainer">																	
			<%@ include file="generatePatientList.jsp"%>
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
						<div style="font-size: 30px;" id="delConfirmModalFieldName"></div>
						<div style="font-size: 15px;" id="delConfirmModalFieldId"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button id="deleteConfirmedButton" type="button" class="btn btn-warning" data-dismiss="modal" onclick="">Delete</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modify model window -->
		<div class="modal" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		  <div class="modal-dialog modal-lg"  role="document">
		    <div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Modify patient</h4>
				</div>
				<div class="modal-body" id="modifyConfirmModalBody">
					<div id="form" class="form-horizontal">
						<input type="hidden" id="id"/>
						<div class="form-group">
							<label for="InputFirstName" class="col-sm-2 control-label">First name</label>
							<div class="col-sm-10">
								<input type="text" class="form-control col-sm-10" style="cursor: auto;" id="firstName"></input>
							</div>
						</div>
						<div class="form-group">
							<label for="InputLastName" class="col-sm-2 control-label">Last name</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor: auto;" id="lastName"/>
							</div>
						</div>
						<div class="form-group">
							<label for="InputDateOfBirth" class="col-sm-2 control-label">Date of birth</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor: auto;" id="dateOfBirth"/>
							</div>
						</div>
						<div class="form-group">
							<label for="InputRoomNumber" class="col-sm-2 control-label">Room number</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor: auto;" id="roomNumber"/>
							</div>
						</div>
						<div class="form-group">
							<label for="InputSocialSecurityNumber" class="col-sm-2 control-label">Social security number</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor: auto;" id="socialSecurityNumber"/>
							</div>
						</div>
						<div class="form-group">
							<label for="InputTelephoneNumber" class="col-sm-2 control-label">Telephone number</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor: auto;" id="telephoneNumber"/>
							</div>
						</div>
						<div class="form-group">
							<label for="InputEmail" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" style="cursor: auto;" id="email"></input>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button id="ModifyConfirmButton" type="button" class="btn btn-warning" data-dismiss="modal" onclick="sendModifyInformation()">Modify</button>
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
	</div>	<!-- container end  -->
</body>
<!-- Bootstrap scripts -->
<script src="js/jquery-1.12.3.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">

	/* DELETE */
	// Set modal info on show
	var callDeleteModal = (function (patientId, patientFrontName, patientLastName) {
	    var deModalElement = document.getElementById('delConfirmModal');
	    var deModalElementButton = document.getElementById('deleteConfirmedButton');
	    var delConfirmModalFieldElementName = document.getElementById('delConfirmModalFieldName');
	    var delConfirmModalFieldElementId = document.getElementById('delConfirmModalFieldId');

	    // Set fields, show window
	    return function (patientId, patientFrontName, patientLastName) {
	    	deModalElementButton.onclick = new Function("sendDeleteInformation("+patientId+")");
	    	delConfirmModalFieldElementName.innerHTML = "Name: " + patientFrontName + " " + patientLastName;
	    	delConfirmModalFieldElementId.innerHTML = "id: "+ patientId;
	    	$("#delConfirmModal").modal();
	    	}
	})();
	function callbackForDelete(xhr, patientId) {
		// Show the alert box
		$("#alertMessageBox").fadeTo(0, 1);
		
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('alertMessageBox') 
		var patientRowElement = document.getElementById("patient_"+patientId+"_row");
		
		// Set the information of the container based on the message
		var jsonObj = JSON.parse(xhr.responseText);
		mssgContainer.innerHTML = jsonObj.message;
		if(jsonObj.succes == true) {
			$("#alertMessageBox").fadeTo(4000, 0);
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
	/* SEARCH */
	function callbackForSearch(xhr, patientId) {
		// Show the alert box
		$("#alertMessageBox").fadeTo(0, 1);
		
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('alertMessageBox')
		var patientsContainer = document.getElementById('patientListContainer')
		var jsonObj = JSON.parse(xhr.responseText);
		
		$.get('FindPatient', function(data) {
		    patientsContainer.innerHTML = data;
		});
		
		// Set the information of the container based on the message
		mssgContainer.innerHTML = jsonObj.message;
		if(jsonObj.succes == true) {
			mssgContainer.setAttribute("class","alert alert-success")
		}
		else {
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}
	function sendSearchPatient() {
		data = {}
		var formContainer = document.getElementById("searchFieldsContainer");
		var inputs = formContainer.getElementsByTagName("input");
		var inputsSize = inputs.length;
		var data = {};
		for (var i = 0; i < inputsSize; i++) {
			data[inputs[i].name] = inputs[i].value;
		}
		load("FindPatient", data, 0, callbackForSearch);
	}
	/* MODIFY */
	// Set modal info on show
	var callModifyModal = (function (id, firstName, lastName, dateOfBirth, roomNumber, socialSecuritynumber, telephoneNumber, email) {
		var modifyid = document.getElementById('id');
	    var modifyfirstName = document.getElementById('firstName');
	    var modifylastName = document.getElementById('lastName');
	    var modifydateOfBirth = document.getElementById('dateOfBirth');
	    var modifyroomNumber = document.getElementById('roomNumber');
	    var modifysocialSecurityNumber = document.getElementById('socialSecurityNumber');
	    var modifytelephoneNumber = document.getElementById('telephoneNumber');
	    var modifyemail = document.getElementById('email');
	    
	    // Set fields, show window
	    return function (id, firstName, lastName, dateOfBirth, roomNumber, socialSecuritynumber, telephoneNumber, email) {
	    	modifyid.setAttribute("value", id);
	    	modifyfirstName.setAttribute("value", firstName);
	    	modifylastName.setAttribute("value", lastName);
	    	modifydateOfBirth.setAttribute("value", dateOfBirth);
	    	modifyroomNumber.setAttribute("value", roomNumber);
	    	modifysocialSecurityNumber.setAttribute("value", socialSecuritynumber);
	    	modifytelephoneNumber.setAttribute("value", telephoneNumber);
	    	modifyemail.setAttribute("value", email);
	    	$("#modifyModal").modal();
	    	}
	})();
	function callbackForModify(xhr, patientId) {
		// Show the alert box
		$("#alertMessageBox").fadeTo(0, 1);
		
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('alertMessageBox')
		var jsonObj = JSON.parse(xhr.responseText);
		
		// Set the information of the container based on the message
		mssgContainer.innerHTML = jsonObj.message;
		if(jsonObj.succes == true) {
			$("#alertMessageBox").fadeTo(4000, 0);
			mssgContainer.setAttribute("class","alert alert-success")
			// The new info
			var modifyModal = document.getElementById("modifyConfirmModalBody");
			var newValuesElements = modifyModal.getElementsByTagName("input");
			
			// Update the row with the info from the modal
			var patientRowElement = document.getElementById("patient_"+patientId+"_row");
			var patientFieldsElements = patientRowElement.getElementsByTagName("td");
			
			// Set the patient field value to the modal field value
			for (var i = 0; i < newValuesElements.length; i++) {
				var elementName = newValuesElements[i].id;
				var newValue = newValuesElements[i].value;
				var patientFieldElement = patientFieldsElements.namedItem('patient_'+elementName);
				patientFieldElement.innerHTML = newValue;
			}
		}
		else {
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}
	function sendModifyInformation() {
		data = {}
		var formContainer = document.getElementById("modifyConfirmModalBody");
		var inputs = formContainer.getElementsByTagName("input");
		var inputsSize = inputs.length;
		var data = {};
		for (var i = 0; i < inputsSize; i++) {
			data[inputs[i].id] = inputs[i].value;
		}
		load("ModifyPatient", data, data["id"], callbackForModify);
	}
	
	/* GENERAL  */
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