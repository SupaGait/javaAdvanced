<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="MainPage">Patient find</a>
		</div>
		<c:if test ="${showMenu == true}">
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<!-- <li class="active"><a href="#">Home</a></li> -->
					<li><a href="MainPage">Home</a></li>
					<li><a href="CreatePatient">Add patients</a></li>
					<li><a href="ListPatients">List patients</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="Login?logout=yes">Logout</a></li>
				</ul>
			</div>
		</c:if>
		<!--/.nav-collapse -->
	</div>
</nav>