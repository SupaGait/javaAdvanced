<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Patient Find</title>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">
<script src="js/bootstrap.js"></script>

<!-- Style sheet -->
<link href="generalLayout.css" rel="stylesheet">



</head>

<body>
	<%@ include file="generalMenu.jsp" %>

	<div id="messageCreatePatient" class="alert alert-success"></div>

	<div id="container">
		<div xmlns="http://www.w3.org/1999/xhtml" class="bs-example">
			<div role="form" lpformnum="1" action="CreatePatient" id="form">
				<div class="form-group">
					<label for="InputFirstName">First name</label> <input type="text"
						class="form-control" style="cursor: auto;" id="firstName"
						name="firstName" placeholder="Enter patient fist name" />
				</div>
				<div class="form-group">
					<label for="InputLastName">Last name</label> <input type="text"
						class="form-control" style="cursor: auto;" id="lastName"
						name="lastName" placeholder="Enter patient" />
				</div>
				<div class="form-group">
					<label for="InputEmail">Email</label> <input type="text"
						class="form-control" style="cursor: auto;" id="email" name="email"
						placeholder="Enter patient" />
				</div>
				<button onclick="sendCreateInformation()" type="submit"
					class="btn btn-default">Submit</button>
			</div>
		</div>
	</div>

	<!-- /.container -->
</body>

<script type="text/javascript">
	function callbackForCreate(xhr) {
		document.getElementById('messageCreatePatient').innerHTML = xhr.responseText;
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