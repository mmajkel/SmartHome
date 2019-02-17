<%@page import="automation.devices.lightSwitch.LightSwitchStatus"%>
<%@page import="automation.devices.lightSwitch.LightSwitch"%>
<%@page import="automation.devices.lightSwitch.LightSwitchHandler"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="automation.main.ViewUserContextDevices"%>
<%@page import="automation.main.UserSessionHandler"%>
<%@page import="org.hibernate.cfg.Configuration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.criterion.Restrictions"%>
<%@page import="automation.main.UserSession"%>
<%@page import="org.hibernate.Criteria"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="${pageContext.request.contextPath}/css/styleNavBar.css" rel="stylesheet" type="text/css" >
 <link href="${pageContext.request.contextPath}/css/styleMyHome.css" rel="stylesheet" type="text/css" >
 <link href="${pageContext.request.contextPath}/css/styleHeader.css" rel="stylesheet" type="text/css" >
 <script src="js/jquery.min.js"></script>
 <script src="js/logoff.js"></script>
 <script src="js/lightSwitches.js"></script>
<title>Make your home smarter</title>

		<%
			String sessionId = null; 
			String username = null;
			
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
			    for (Cookie cookie : cookies) {
			        if( cookie.getName().toString().equals("JSESSIONID")){
			        	sessionId = cookie.getValue().toString();
			        }
			    }
			}
			    //select do tabeli 
				UserSessionHandler u = new UserSessionHandler(sessionId);
			    username = u.getUsername();
		 				if(username == "false"){
				    response.sendRedirect(request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/")));
		 				}
		%>

</head>
<body>
<header>
	<input type="checkbox" id="navToggle" class="navToggle">
	<div id="logo">
		<p>X[i]</p>
	</div>
	<form id="logoff" action="#" method="post">
		<input id='logoffInput' type="text" name="login" 
		value="<%out.println(username);%>"
		/>
		<input id="logoffSubmit" type="submit" value="wyloguj"/>
	</form>
	<nav>
		<ul>
			<li><a href ="myhome">my devices</a></li>
			<li><a href ="#">my profile</a></li>
			<li><a href ="#">my schedule</a></li>
			<li><a href ="mydrive">my drive</a></li>
		</ul>	
	</nav>
	<label for="navToggle" class="navToggleLabel"><span></span></label>
</header>
	<main>
			<%
				ViewUserContextDevices uc = new ViewUserContextDevices(username);
				
					 	if(username != "false"){
				//		MapDevices md = new MapDevices();
						out.print("<div id=devices>");
			 					for(int i=0; i<uc.getContextSize(); i++){
							out.print("<div class='singleDevice'>"
									+"<button class='device' id='"
					 						+uc.getUserContext().get(i).getDeviceId().trim()
			 								+"'"
			 								//+"style=\"background-image: url(./img/wl.jpg)\""
					 						+">"
			 								+"<img src='./img/wl.jpg' width='90' height='150' />"
			 								+"<p>"+uc.getUserContext().get(i).getDeviceName().trim()+"</p>"
					 						+"</button>"
					 						+"</div>");
						
						}
			 				 	out.print("</div>");
					}
			%>
	</main>
 
<script>
<%
for(int i=0; i<uc.getContextSize(); i++){	
	String deviceId = uc.getUserContext().get(i).getDeviceId().trim();
out.println("var status"+deviceId+";");
}

out.println(""
		+"$(document).ready(function(e){"
	//	+"	setTimeout(reload,10000);"
		+"reload();"
		+"});"
		);
	out.println("function reload(){");
//	out.println("console.log('reload')");

	for(int i=0; i<uc.getContextSize(); i++){	
		String deviceId = uc.getUserContext().get(i).getDeviceId().trim();
	out.println("var formData"+deviceId+" = {");
	out.println("		'deviceId' : '"+deviceId+"'");
	out.println("	} ");
  
  out.println("$.ajax({");
  out.println("   url: './myhome/lightswitchesStatus',");
  out.println("   data: formData"+deviceId+",");
  out.println("   type: 'POST',");
  out.println("    success: function(result) {");

	  out.println("		 if (result == '1'){"
		+"$('#"+deviceId+"'"
		+").find('img').attr('src','./img/wl.jpg');");
		out.println("status"+deviceId+" = 1;");
	  out.println("		 }if (result == '0'){"
		+"$('#"+deviceId+"'"
		+").find('img').attr('src','./img/wyl.jpg');");
		out.println("status"+deviceId+" = 0;");  	  
	  out.println("		 }if (result > 1){"
			+"$('#"+deviceId+"'"
			+").find('img').attr('src','./img/notavailable.jpg');");
	  out.println("}");		  
  out.println("	},");
out.println("		error: function() {");
out.println("         return false;");
out.println("   }");
out.println(" }); ");
	
//	out.println("	console.log('aktualny status = '+status"+deviceId+");");
	}
	out.println("	setTimeout(reload,500);");
	out.println("} \n");
	
	
	
 for(int i=0; i<uc.getContextSize(); i++){
	String deviceId = uc.getUserContext().get(i).getDeviceId().trim();
	out.println("var sendStatus"+deviceId+"=1;");

	out.println("$('#"+deviceId+"').click(function() {");
		out.println("if(status"+deviceId+" == 1){");
			out.println("sendStatus"+deviceId+" = 0;}");
		out.println("if(status"+deviceId+" == 0){");
			out.println("sendStatus"+deviceId+" = 1;}");
	//	out.println("console.log('zmieniony status = '+sendStatus"+deviceId+");");
		out.println("var formData = {");
		out.print("		'status' 	:sendStatus"+deviceId+",");
		out.println("		'client' : '"+uc.getUserContext().get(i).getDeviceId().trim()+"',");
		out.println("		'callerType'	:'p'");
		out.println("	} ");
	  
	  out.println("$.ajax({");
	  out.println("   url: './myhome/lightswitches',");
	  out.println("   data: formData,");
	  out.println("   type: 'POST',");
	  out.println("   beforeSend : function(){");
	  out.println("        $('#deviceWaiting').css('display', 'block');");
	  out.println("      }," );
	  out.println("    success: function(result) {");
	  out.println("		 if (result == '1'){");
					//ustawić zmienną src na wartość z tabeli mapowań ze statusem 1
	  out.println("		 }if (result == '0'){");
					//ustawić zmienną src na wartość z tabeli mapowań ze statusem 1	 
	  out.println("		 }");
	  out.println("		 $('#deviceWaiting').css('display', 'none');");
	  out.println("	},");
	out.println("		error: function() {");
	out.println("         return false;");
	out.println("   }");
	out.println(" }); ");
	/*	  out.println("console.log('koniec "
 	  +uc.getUserContext().get(i).getDeviceId().trim()
	  			+"');");
 */	out.println("}); \n");
} 
%>
</script>
	
	
	<footer>
		
	</footer>
</body>
</html>