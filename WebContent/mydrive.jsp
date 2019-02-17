<%@page import="automation.main.UserSessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="${pageContext.request.contextPath}/css/styleNavBar.css" rel="stylesheet" type="text/css" >
 <link href="${pageContext.request.contextPath}/css/styleMyDrive.css" rel="stylesheet" type="text/css" >
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
			<li><a href ="#mydrive">my_drive</a></li>
		</ul>	
	</nav>
	<label for="navToggle" class="navToggleLabel"><span></span></label>
</header>
	<main>

		<div class='driveContent'>
		
			<p id='path'></p>
			<button id='back' onclick='back()'>WSTECZ</button>
			<div id='content'>
			
			</div>
		</div>
		
	</main>
 
 <script>
 
 var mainPath = "D:/";
	$(document).ready(function(e){

	getFiles(mainPath);

	});
	function getFiles(path){
		updatePath(path);
		
		var formData = {
				'path' : path
			} 
		$.ajax({
		   url: './mydrive/getFiles',
		   data: formData,
		   type: 'POST',
		    success: function(result) {
		    	changeContent(result);
			},
			error: function() {
       		 return false;
  			}
 		});
	};
	
	function changeContent(result){
	
		var array = result.split('\n');
		console.log(array);
		
		for(var i=0;i<array.length-1;i++){
			var extention = array[i].split('.')[1];
				//console.log(extention);
				//console.log("--- " + typeof extention);
 				if (isJpg(extention)){
					//console.log("znalazłem jpg, src=" + mainPath+array[i].substring(0, array[i].length-1) );
					$('#content').append("<img src="+mainPath+array[i].substring(0, array[i].length-1) + ">");
	 				
 				}else{
					$('#content').append("<button onclick='getFiles(\""+mainPath+array[i].substring(0, array[i].length-1)+"/\")'>"+array[i]+"</button>");
				}
		}

	}
	
	function isJpg(extention){
		var returnValue = false;
		if(typeof extention !== 'undefined'){
		
			var extentionArray = extention.split('');
			var jpgExtentionArray = ["j","p","g"];
			
			for (var i=0; i<extentionArray.length - 1; i++){
				if (jpgExtentionArray[i] == extentionArray[i]){
					returnValue = true;
				}else{
					return false;
				}
				//console.log("Array["+i+"] = " + extentionArray[i]);
			}
		}
		
		if (returnValue == true){
			return true;
		}else{
			return false;
		}
	}
	
 	function updatePath(path){
		$('#content').text(path);
 		mainPath = path;
 	}	 
 	
 	function back(){
 		var previousPath = "";
 		var pathArray = mainPath.split('/');
 		for(var i=0;i<pathArray.length-2;i++){
 			previousPath = previousPath + pathArray[i] + '/';
 		}
 	//	console.log(previousPath);
 		getFiles(previousPath);
 		
 	}
 	
 </script>
 
 
	<footer>
		
	</footer>
</body>
</html>