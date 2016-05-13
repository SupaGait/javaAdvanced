<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Patient Find - Home</title>
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
		
		<div id="loginForm" class="form-horizontal">
			<div class="form-group">
				<label for="exampleInputEmail1"  class="col-sm-2 control-label">Login</label> 
				<div class="col-sm-4">
					<input type="text" class="form-control" id="userName" name="userName"  placeholder="Enter user name"/>
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-4"> 
					<input type="password" class="form-control" id="password" name="password"  placeholder="Password"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-sm-offset-2">
					<button  onclick="sendLoginInformation()"  type="submit" class="btn btn-primary">Login</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /.container -->
</body>
<!-- Bootstrap scripts -->
<script src="js/jquery-1.12.3.min.js"></script>
<script src="js/bootstrap.js"></script>


<script type="text/javascript">
	function callbackForLogin(xhr) {
		// Get the container, and the response and parse it to an JSON object
		var mssgContainer = document.getElementById('alertMessageBox')
		var jsonObj = JSON.parse(xhr.responseText);
		
		// If succes, request redirect, otherwise show alert
		if(jsonObj.succes == true) {
			var endPos = location.href.lastIndexOf("/")
			var startUrl = location.href.substring(0, endPos)
			location.href= startUrl + "/Login";
		}
		else {
			mssgContainer.innerHTML = jsonObj.message;
			mssgContainer.setAttribute("class","alert alert-danger")
		}
	}

	function sendLoginInformation() {
		data = {}
		var formContainer = document.getElementById("loginForm");
		var inputs = formContainer.getElementsByTagName("input");
		var inputsSize = inputs.length;
		var data = {};
		for (var i = 0; i < inputsSize; i++) {
			data[inputs[i].name] = inputs[i].value;
		}
		load("Login", data, callbackForLogin);
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