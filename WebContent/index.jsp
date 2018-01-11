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
 	display:none;
}

</style>


<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript">

function reload(){
	$("#statusBiezacy").load(" #statusBiezacy");
	setTimeout(reload,300);

}

</script>
</head>
<body onload="reload()">
<!--  !! zmienić sposób przesyłania formularza na JQuery  -->
	<iframe class="hidden" name="hidden"></iframe>

	<div id="statusBiezacy">
	
		<div id="status" ><a target="hidden" href="witaj?status=<% 
		
		if(Server.lightSwitchesList.get(0).getStatusBiezacy() == 1){
			out.print("0");
		}else{
			out.print("1");
		}
		out.print("&client=p"+Server.lightSwitchesList.get(0).getId());
		
		%>">
		
		<%
		
		if(Server.lightSwitchesList.get(0).getStatusBiezacy() == 1){
			out.print("<img src='img/wl.jpg'");
		}else{
			out.print("<img src='img/wyl.jpg'");
		}
		
		%>
		
		</a></div>
		
		<div id="status" ><a target="hidden" href="witaj?status=<% 
		
		if(Server.lightSwitchesList.get(1).getStatusBiezacy() == 1){
			out.print("0");
		}else{
			out.print("1");
		}
		out.print("&client=p"+Server.lightSwitchesList.get(1).getId());
		
		%>">
		
		<%
		
		if(Server.lightSwitchesList.get(1).getStatusBiezacy() == 1){
			out.print("<img src='img/wl.jpg'");
		}else{
			out.print("<img src='img/wyl.jpg'");
		}
		
		%>
		
		</a></div>
		
	
	</div>
	
	<br/>
	<br/>
	
	
</body>
</html>