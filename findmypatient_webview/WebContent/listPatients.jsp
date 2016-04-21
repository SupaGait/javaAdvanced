<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<%@ page import="fr.gklomphaar.findmypatient.datamodel.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	
	<!-- Style sheets, Bootstrap & cutom -->
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="generalLayout.css" rel="stylesheet">
	
</head>
<body>
	<%@ include file="generalMenu.jsp" %>

	<div class="container"> 
	  <h2>Patient</h2>
	  <!--Search Form -->
	  <form action="/employee" method="get" id="seachEmployeeForm" role="form" >
	    <input type="hidden" id="searchAction" name="searchAction" value="searchByName"/>
	    <div class="form-group col-xs-5">
	        <input type="text" name="employeeName" id="employeeName" class="form-control" required="true" 
	                 placeholder="Search patient"/>                    
	    </div>
	    <button type="submit" class="btn btn-info">
	        <span class="glyphicon glyphicon-search"></span> Search
	    </button>
	    <br></br>
	    <br></br>
	  </form>
	  
	  <form action="/ListPatients" method="post" id="patientsForm" role="form" >    
		<c:choose>
	      <c:when test="${not empty patientsList}">
	          <table  class="table table-striped">
	              <thead>
	                  <tr>
	                      <td>#</td>
	                      <td>First Name</td>
	                      <td>Last name</td>
	                      <td>Birth date</td>
<!-- 	                      <td>Room</td>
	                      <td>Email</td>
	                      <td>E-mail</td> -->
	                  </tr>
	              </thead>
	              <c:forEach var="patient" items="${patientsList}">
	                  <c:set var="classSucess" value=""/>
	                  <%-- <c:if test ="${idEmployee == employee.id}">                           
	                      <c:set var="classSucess" value="info"/>
	                  </c:if> --%>
	                  <tr class="${classSucess}">
	                      <td>${patient.pId}</td>
	                      <td>${patient.fName}</td>
	                      <td>${patient.lName}</td>
	                      <td>${patient.dob}</td>
	                      <%-- <td>${patient.}</td>
	                      <td>${patient.}</td>
	                      <td>${patient.}</td> --%>                        
	                  </tr>
	              </c:forEach>               
	          </table>  
	      </c:when>                    
	      <c:otherwise>
	      <br>  </br>           
	          <div class="alert alert-info">
	              No people found matching your search criteria
	          </div>
	      </c:otherwise>
	  </c:choose>                        
	</form>

	</div> 

    <%-- <h1>${list.firstName} ${student.lastName}</h1> --%>
</body>
<!-- Bootstrap scripts -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
</html>