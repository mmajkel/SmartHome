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

	
	/*
	if (status == "1"){
		document.getElementById("wl1").style.display = "none";
		document.getElementById("wyl1").style.display = "block";
		
	}else if(status == "0"){
		document.getElementById("wyl1").style.display = "none";
		document.getElementById("wl1").style.display = "block";
	}
	
	*/
	setTimeout(reload,9000);

}

</script>
</head>
<body onload="reload()">
<!--  !! zmienić sposób przesyłania formularza na JQuery  -->
	<iframe class="hidden" name="hidden"></iframe>

	<div id="statusBiezacy">
	<a id="wl1" target="hidden" href="witaj?status=1&client=p<%
		out.print(Server.lightSwitchesList.get(0).getId());
	%> ">WL</a></br>
	<a id="wyl1" target="hidden" href="witaj?status=0&client=p<%
		out.print(Server.lightSwitchesList.get(0).getId());
	%> ">WYL</a>
		<div id="status"><% out.print(Server.lightSwitchesList.get(0).getStatusBiezacy());%></div>
	
	</br>
	</br>
	
	<a id="wl2" target="hidden" href="witaj?status=1&client=p<%
		out.print(Server.lightSwitchesList.get(1).getId());
	%> ">WL</a></br>
	<a id="wyl2" target="hidden" href="witaj?status=0&client=p<%
		out.print(Server.lightSwitchesList.get(1).getId());
	%> ">WYL</a>
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
			out.print("WL");
		}else{
			out.print("WYL");
		}
		
		%>
		
		</a></div>
	
	
	
	
	</div>
	
	<br/>
	<br/>
	
	
</body>
</html>