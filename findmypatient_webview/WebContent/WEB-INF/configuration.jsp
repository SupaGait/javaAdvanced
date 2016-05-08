<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Welcome to patient find</title>
	<!-- Style sheets, Bootstrap & custom -->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="css/generalLayout.css" rel="stylesheet">
</head>
<body>
    <div class="jumbotron">
        <div class="container">
            <h1 class="text-info">Welcome to the Patient find System </h1>
        </div>
    </div>
    <div class="container">
    	<div class="row">
			<div class="col-sm-5 col-sm-offset-2">
				<div id="alertMessageBox" class="alert hidden">Message</div>
			</div>
		</div>
        <div class="page-header">
            <h2>Configuration</h2>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <p>It seems it is the first time this page is accessed, please provide administrator information: </p>
            </div>
        </div>
        <div id="form" class="form-horizontal">
			<div class="form-group">
				<label for="InputUserName" class="col-sm-2 control-label">user name: </label>
				<div class="col-sm-4">
					<input type="text" class="form-control col-sm-10" style="cursor: auto;" id="userName" name="userName" placeholder="User name" />
				</div>
			</div>
			<div class="form-group">
				<label for="InputPassword" class="col-sm-2 control-label">password:</label>
				<div class="col-sm-4">
					<input type="password" class="form-control" style="cursor: auto;" id="password" name="password" placeholder="Password" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-4">
					<button onclick="sendCreateInformation()" type="submit" class="btn btn-primary ">Create administrator</button>
				</div>
			</div>
		</div>
    </div>
</body>
<!-- Bootstrap scripts -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">

	function callbackForCreate(xhr) {
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('alertMessageBox')
		var jsonObj = JSON.parse(xhr.responseText);
		
		// If succes, request redirect, otherwise show alert
		if(jsonObj.succes == true) {
			var endPos = location.href.lastIndexOf("/")
			var startUrl = location.href.substring(0, endPos)
			location.href= startUrl + "/MainPage";
		}
		else {
			mssgContainer.innerHTML = jsonObj.message;
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}

	function sendCreateInformation() {
		data = {}
		var formContainer = document.getElementById("form");
		var inputs = formContainer.getElementsByTagName("input");
		var inputsSize = inputs.length;
		var data = {};
		for (var i = 0; i < inputsSize; i++) {
			data[inputs[i].name] = inputs[i].value;
		}
		load("ConfigWebsite", data, callbackForCreate);
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