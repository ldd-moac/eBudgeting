<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
	<div class="span12"><img src="<c:url value="/resources/graphics/header1.gif"/>"/> 
	</div>
</div>

<div class="navbar">
		<div class="container">
		 	
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a class="brand" href="<c:url value='/'/>">หน้าหลัก</a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					Logged in as
						<sec:authentication property="principal.username"/> @ <sec:authentication property="principal.workAtAbbr"/> | <a class="navbar-link" href="<c:url value='/logout'/>">Logout</a>
				</p>
			</div>
			
			<!--/.nav-collapse -->
		</div>

</div>