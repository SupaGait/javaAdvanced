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
	<%@ include file="generalMenu.jsp" %>
	
    <div class="jumbotron">
        <div class="container">
            <h1 class="text-info">Welcome to the Patient find System </h1><a href="#">disconnect</a>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-xs-6">
                <h4>Patient Creation</h4>

                <p>Thanks to this action, you can create a brand new Patient, you can click on the button below to
                    begin</p>
                <a  href="CreatePatient"><button>Create new patient!</button></a>
            </div>
            <div class="col-xs-6">
                <h4>Patient Search</h4>

                <p>Thanks to this action, you can search an patient and then access to its information. Through this
                    action, you can also modify or delete the wished patient</p>
                <a href="ListPatients"><button>Show and search patients</button></a>
            </div>
        </div>
    </div>
</body>
<!-- Bootstrap scripts -->
<script src="js/jquery-1.12.3.min.js"></script>
<script src="js/bootstrap.js"></script>
</html>