<%@page import="automation.main.UserSessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="${pageContext.request.contextPath}/css/styleNavBar.css" rel="stylesheet" type="text/css" >
 <link href="${pageContext.request.contextPath}/css/styleLogin.css" rel="stylesheet" type="text/css" >
 <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" >
 <link href="${pageContext.request.contextPath}/css/styleHeader.css" rel="stylesheet" type="text/css" >
 <script src="js/jquery.min.js"></script>
 <script src="js/login.js"></script>
 <script src="js/animateBackground.js"></script>
 <script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="337627846521-h6frn6s4o4jsh1kggl66n9r5m2fla546.apps.googleusercontent.com">
<title>Make your home smarter</title>

</head>
<body>
<header>
	<input type="checkbox" id="navToggle" class="navToggle">
	<div id="logo">
		<p>X[i]</p>
	</div>
	<nav>
		<ul>
			<li><a href ="#aboutXi">O xi</a></li>
			<li><a href ="#kickstarter">Kickstarter</a></li>
			<li><a href ="#shop">Sklep</a></li>
		</ul>	
	</nav>
	<label for="navToggle" class="navToggleLabel"><span></span></label>
</header>
	<main>
		<div id="homeImg">
		</div>
			
			<!--  <button id="googleLo	ginButton">przejdz do myhome</button>
			 <a href="#" onclick="signOut();">Sign out</a>
		 -->
		
		<div id="login">
<%-- 			<%
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
			    
					
			out.print(
				"<h1>Masz już konto?</h1>"
				+"<form id='loginForm' action='#' method='post'>"
				+"<input class='inputCredentials' id='username' type='text' name='login' placeholder='Nazwa użytkownika'/>"
				+"<input class='inputCredentials' id='password' type='password' name='password' placeholder='hasło'/>"
				+"<input id='submit' type='submit' value='zaloguj'/>"
				+"</form>"
				+"<div id='loginWaiting'>"
				+	"<img src='img/ring_loader.svg'>"
				+"</div>"
				+"<p id='loginResult'>"
				+"</p>"
				+"<div id='formResult'>"
				+"</div>"
				+"<h2>Lub</h2>"
						+"<form id='signInForm' action='#' method='post'>"
						+"<input class='inputCredentials' id='signInEmail' type='text' name='email' placeholder='Email'/>"
						+"<input class='inputCredentials' id='signInLogin' type='text' name='login' placeholder='Nazwa użytkownika'/>"
						+"<input class='inputCredentials' id='signInPassword' type='password' name='password' placeholder='Hasło'/>"
						+"<input id='submitSignIn' type='submit' value='zarejestruj'/>"
						+"</form>"
				+"</h2>"
				+" <div class='g-signin2' data-onsuccess='onSignIn' id='myP'></div>"
				);
				
			}else{
				out.print("<p>Witaj "+username+"! </p>"
						+"<a href='./myhome'>Przejdz do panelu sterowania domem</a>");
			}
 			%>
--%>

		<div id='loginFormContainer'>
			<p>Logowanie</p>
				 <form id='loginForm' action='#' method='post'> 
				 <input class='inputCredentials' id='username' type='text' name='login' placeholder='Nazwa użytkownika'/> 
				 <input class='inputCredentials' id='password' type='password' name='password' placeholder='hasło'/> 
				 <input class='submitButton' id='submit' type='submit' value='zaloguj'/> 
				 </form> 
					 <div id='loginWaiting'> 
						 <img src='img/ring_loader.svg'> 
					 </div> 
					 <p id='loginResult'></p> 
				<div id='googleLoginButton' class="g-signin2"
				 data-width="200" data-height="50"
				 data-onsuccess="onSignIn"></div>
<!-- 	  		  <div id="gSignInWrapper">
			    <div id="customBtn" class="customGPlusSignIn">
			      <span class="icon"></span>
			      <span class="buttonText">Zaloguj z Google</span>
			    </div>
			  </div>
			  <div id="name"></div>
			  <script>startApp();</script> -->
  			<p>Nie masz konta? <a id='registerButton'>Zarejestruj się</a></p>
	  	</div>
		
		

		<div id='signInFormContainer'>
			<p>Rejestracja</p>
				 <form id='signInForm' action='#' method='post'> 
					 <input class='inputCredentials' id='signInEmail' type='text' name='email' placeholder='Email'/> 
   					 <input class='inputCredentials' id='signInPassword' type='password' name='password' placeholder='Hasło'/> 
					 <input class='submitButton' id='submitSignIn' type='submit' value='zarejestruj'/> 
				 </form>
					 <div id='signInWaiting'> 
						 <img src='img/ring_loader.svg'> 
					 </div> 
					 <p id='signInResult'></p> 

			<p>Masz już konto? <a id='loginButton'>Zaloguj się</a></p>
		</div>
		</div>
		
		
		<section>
			<div id=firstImg>
							
			</div>
		</section>
		<footer>
		
		</footer>
	</main>
</body>
</html>