<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Create patient</title>
	
	<!-- Style sheets, Bootstrap & custom -->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="css/generalLayout.css" rel="stylesheet">
</head>

<body>
	<%@ include file="generalMenu.jsp" %>
	
	<div class="container">
		<div class="row">
			<div class="col-sm-5 col-sm-offset-2">
				<div id="alertMessageBox" class="alert invisible">Message</div>
			</div>
		</div>
		<div id="form" class="form-horizontal">
			<div class="form-group">
				<label for="InputFirstName" class="col-sm-2 control-label">First name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control col-sm-10" style="cursor: auto;" id="firstName" name="firstName" placeholder="Enter patient first name" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputLastName" class="col-sm-2 control-label">Last name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" style="cursor: auto;" id="lastName" name="lastName" placeholder="Enter patient last name" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputDateOfBirth" class="col-sm-2 control-label">Date of birth</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" style="cursor: auto;" id="dateOfBirth" name="dateOfBirth" placeholder="Enter patient date of birth" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputRoomNumber" class="col-sm-2 control-label">Room number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" style="cursor: auto;" id="roomNumber" name="roomNumber" placeholder="Enter patient room number" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputSocialSecurityNumber" class="col-sm-2 control-label">Social security number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" style="cursor: auto;" id="socialSecurityNumber" name="socialSecurityNumber" placeholder="Enter patient social security number" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputTelephoneNumber" class="col-sm-2 control-label">Telephone number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" style="cursor: auto;" id="telephoneNumber" name="telephoneNumber" placeholder="Enter patient telephone number" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" style="cursor: auto;" id="email" name="email" placeholder="Enter patient email address" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button onclick="sendCreateInformation()" type="submit" class="btn btn-primary ">Submit</button>
				</div>
			</div>
		</div>
	</div><!-- /.container -->
</body>
<!-- Bootstrap scripts -->
<script src="js/jquery-1.12.3.min.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
	function callbackForCreate(xhr) {
		$("#alertMessageBox").fadeTo(0, 1);
		
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('alertMessageBox')
		var jsonObj = JSON.parse(xhr.responseText);
		
		// Set the information of the container based on the message
		mssgContainer.innerHTML = jsonObj.message;
		if(jsonObj.succes == true) {
			mssgContainer.setAttribute("class","alert alert-success")
			$("#alertMessageBox").fadeTo(4000, 0);
		}
		else {
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}

	function sendCreateInformation() {
		data = {}
		var formContainer = document.getElementById("form");
		var inputs = formContainer.getElementsByTagName("input");
		var inputsSize = inputs.length;
		var data = {};
		;
		for (var i = 0; i < inputsSize; i++) {
			data[inputs[i].name] = inputs[i].value;
		}
		load("CreatePatient", data, callbackForCreate);
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