$(document).ready(function(e){

	animateBackground();
	window.setInterval(animateBackground, 30000);
});

function animateBackground(){
	
	var d = new Date();
	var seconds = d.getSeconds()+1;
	var minutes = d.getMinutes()*60+1;
	var hours = d.getHours()*60*60+1;
	
	var hoursInSeconds = hours+minutes+seconds;
	
	console.log(hoursInSeconds);
//  dla testów pełen obrót w minutę - zmienić czas odświeżania na 1 sekundę	
//	var scaledSeconds = seconds*360/60;
	var scaledSeconds = hoursInSeconds*360/86400;
	console.log(scaledSeconds);

	$('main').css('background-image','linear-gradient('+scaledSeconds+'deg, yellow, #140084,#140084)')
};