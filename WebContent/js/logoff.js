var loginSuccess = false;
var formData;
var myform;

$(document).ready(function(e){

	$("#logoff").submit(function(e) {
		  
		//signOut();
		
		var formData = {
			'login' 	:$('input[name=login]').val()
		}
		
		e.preventDefault();
        $.ajax({
            url: "myhome/logoff",
            data: formData,
            type: "POST",
            beforeSend : function(){
                $('#loginWaiting').css('display', 'block');
             }, 
            success: function(result) {
				 if (result == 'ok'){
					 console.log('ok');
					 window.location.href = "./";
				 }else{
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
});


function signOut() {
	console.log("signout");
	var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }
