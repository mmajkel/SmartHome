<%@page import="automation.server.Server"%>
<%@page import="automation.lightSwitch.LightSwitch"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Light switch</title>

<style>
	
.hidden{
 	position:absolute;
 	top:-1px;
 	left:-1px;
 	width:1px;
 	height:1px; 
}
	
</style>


<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript">

function reload(){
	$("#statusBiezacy").load(" #statusBiezacy");
	
		
		
	
	
	setTimeout(reload,600);
}


</script>
</head>
<body onload="reload()">

	<iframe class="hidden" name="hidden"></iframe>

	<a target="hidden" href="witaj?status=1&client=p<%
		out.print(Server.lightSwitchesList.get(0).getId());
	%> ">WL</a></br>
	<a target="hidden" href="witaj?status=0&client=p<%
		out.print(Server.lightSwitchesList.get(0).getId());
	%>">WYL</a>
	
	<div id="statusBiezacy">
	
	<%
	out.print(Server.lightSwitchesList.get(0).getStatusBiezacy());
	
	%>
	
	</div>
	
	</br>
	</br>
	
	<a href="witaj?status=1&client=p<%
			out.print(Server.lightSwitchesList.get(1).getId());
	%>">WL</a></br>
	<a href="witaj?status=0&client=p<%
			out.print(Server.lightSwitchesList.get(1).getId());
	%>">WYL</a>
	
</body>
</html>