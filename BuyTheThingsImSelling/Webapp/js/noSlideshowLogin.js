//Event listener for esc key on lightBox - Do Not Use when slideshow.js is present
$(window).on('load', function() { 
	document.onkeydown = function(evt) {
	    evt = evt || window.event;
	    var isEscape = false;
	    if ("key" in evt) {
	        isEscape = (evt.key == "Escape" || evt.key == "Esc");
	    } else {
	        isEscape = (evt.keyCode == 27);
	    }
	    if (isEscape) {
	    	$('#login-box-modular').css({'display':'none'});
	    }
	};
	$('#loginLightBoxBG').click(function(){
		$('#login-box-modular').css({'display':'none'});
	});
});