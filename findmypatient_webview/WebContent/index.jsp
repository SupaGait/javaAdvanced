<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Patient Find - Home</title>
	<!-- Style sheets, Bootstrap & custom -->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="generalLayout.css" rel="stylesheet">
</head>

<body>
	<%@ include file="generalMenu.jsp" %>

	<div class="container">
		<form class="form-horizontal">
			<div class="form-group">
				<label for="exampleInputEmail1"  class="col-sm-2 control-label">Login</label> 
				<div class="col-sm-4">
					<input type="text" class="form-control" id="exampleInputEmail1" placeholder="Enter user name"/>
				</div>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-4"> 
					<input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-sm-offset-3">
					<button type="submit" class="btn btn-default">Login</button>
				</div>
			</div>
		</form>
	</div>
	<!-- /.container -->
</body>
<!-- Bootstrap scripts -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
</html>