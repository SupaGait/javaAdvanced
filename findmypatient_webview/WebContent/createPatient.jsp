<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Create patient</title>
	
	<!-- Style sheets, Bootstrap & custom -->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="generalLayout.css" rel="stylesheet">
</head>

<body>
	<%@ include file="generalMenu.jsp" %>
	<div id="messageCreatePatient" class="alert"></div>
	<div id="container">
		<div>
			<div role="form" lpformnum="1" action="CreatePatient" id="form">
				<div class="form-group">
					<label for="InputFirstName">First name</label> 
					<input type="text" class="form-control" style="cursor: auto;" id="firstName" name="firstName" placeholder="Enter patient first name" />
				</div>
				<div class="form-group">
					<label for="InputLastName">Last name</label>
					<input type="text" class="form-control" style="cursor: auto;" id="lastName" name="lastName" placeholder="Enter patient last name" />
				</div>
				<div class="form-group">
					<label for="InputDateOfBirth">Date of birth</label>
					<input type="text" class="form-control" style="cursor: auto;" id="dateOfBirth" name="dateOfBirth" placeholder="Enter patient date of birth" />
				</div>
				<div class="form-group">
					<label for="InputRoomNumber">Room number</label>
					<input type="text" class="form-control" style="cursor: auto;" id="roomNumber" name="roomNumber" placeholder="Enter patient room number" />
				</div>
				<div class="form-group">
					<label for="InputSocialSecurityNumber">Social security number</label>
					<input type="text" class="form-control" style="cursor: auto;" id="socialSecurityNumber" name="socialSecurityNumber" placeholder="Enter patient social security number" />
				</div>
				<div class="form-group">
					<label for="InputTelephoneNumber">Telephone number</label>
					<input type="text" class="form-control" style="cursor: auto;" id="telephoneNumber" name="telephoneNumber" placeholder="Enter patient telephone number" />
				</div>
				<div class="form-group">
					<label for="InputEmail">Email</label>
					<input type="text" class="form-control" style="cursor: auto;" id="email" name="email" placeholder="Enter patient email address" />
				</div>

				<button onclick="sendCreateInformation()" type="submit" class="btn btn-default">
					Submit
				</button>
			</div>
		</div>
	</div><!-- /.container -->
</body>
<!-- Bootstrap scripts -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
	function callbackForCreate(xhr) {
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('messageCreatePatient')
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