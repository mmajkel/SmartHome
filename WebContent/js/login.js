var loginSuccess = false;
var formData;
var myform;
var profile;
var idToken;
var name;






function onSignIn(googleUser) {
//	  var profile = googleUser.getBasicProfile();
	  idToken = googleUser.getAuthResponse().id_token;
/*	  name = profile.getName();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('id token: '+idToken);
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
*/	  googleUser.disconnect();

	  loginWithGoogle();
}



function loginWithGoogle(){	
	
	console.log("wciśnieto zaloguj przez google");
	console.log("token: "+idToken);
	var formData = {
		'tokenId' 	:idToken,
	}
		
    $.ajax({
        url: "validateGoogleUser",
        data: formData,
        type: "POST",
            success: function(result) {
            	console.log("wynik wywołania: "+result)
			 if (result == 'ok'){
				 $('#googleLoginButton').replaceWith("Zalogowano przez google");
				 window.location.href = "./myhome";
			 }else{
	             $('#loginWaiting').css('display', 'none');
				 $('#loginResult').text(result);
			 }
        },
        error: function() {
            $('#loginWaiting').css('display', 'none');
            $('#loginResult').text("Błędne dane logowania");
            return false;
        }
    });
}



function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }


$(document).ready(function(e){
	
	$("#loginForm").submit(function(e) {

		var formData = {
			'login' 	:$('#username').val(),
			'password'	:$('#password').val()
		}
		
		e.preventDefault();
        $.ajax({
            url: "validateUser",
            data: formData,
            type: "POST",
            beforeSend : function(){
                $('#loginWaiting').css('display', 'block');
             }, 
            success: function(result) {
				 if (result == 'ok'){
					 console.log('ok');
					 window.location.href = "./myhome";
				 }else{
		             $('#loginWaiting').css('display', 'none');
					 $('#loginResult').text(result);
				 }
            },
            error: function() {
                $('#loginWaiting').css('display', 'none');
            	$('#loginResult').text("Błędne dane logowania");
                return false;
            }
        });
       
	});
	
	
	$("#signInForm").submit(function(e) {

		e.preventDefault();
		console.log("zarejestruj");
		var loginForSignIn = $('#signInEmail').val()
								.substring(0,
								$('#signInEmail').val().indexOf("@"));
		console.log(loginForSignIn);
		var formData = {
			'email'		:$('#signInEmail').val(),
			'login' 	:loginForSignIn,
			'password'	:$('#signInPassword').val()
		}
		
		e.preventDefault();
        $.ajax({
            url: "signInUser",
            data: formData,
            type: "POST",
            beforeSend : function(){
                $('#signInWaiting').css('display', 'block');
             }, 
            success: function(result) {
				 if (result == 'ok'){
					 console.log('ok');
					 window.location.href = "./myhome";
				 }else{
		             $('#signInWaiting').css('display', 'none');
					 $('#signInResult').text(result);
				 }
            },
            error: function() {
                $('#signInWaiting').css('display', 'none');
            	$('#signInWaiting').text("Błędne dane rejestracji");
                return false;
            }
        });
       
	});	

	$("#registerButton").click(function(e) {
		console.log("wciśnięto regusterButton");
		$('#signInFormContainer').css('display','block');
		$('#loginFormContainer').css('display','none');
	});

	$("#loginButton").click(function(e) {
		console.log("wciśnięto loginButton");
		$('#signInFormContainer').css('display','none');
		$('#loginFormContainer').css('display','block');
	});


});


//custom google login button
/*var googleUser = {};
var startApp = function() {
  gapi.load('auth2', function(){
    auth2 = gapi.auth2.init({
      client_id: '337627846521-h6frn6s4o4jsh1kggl66n9r5m2fla546.apps.googleusercontent.com',
      cookiepolicy: 'single_host_origin',
    });
    attachSignin(document.getElementById('customBtn'));  
  
  });
};

function attachSignin(element) {
  console.log(element.id);
  auth2.attachClickHandler(element, {},
      function(googleUser) {
        idToken = googleUser.getAuthResponse().id_token;
        googleUser.disconnect();
        loginWithGoogle();

  	}, function(error) {
//  		console.log('zly ');
  	//	alert(JSON.stringify(error, undefined, 2));
      });
}
*/


