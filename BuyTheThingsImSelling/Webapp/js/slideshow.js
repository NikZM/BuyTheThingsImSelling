$(window).on('load', function() { 
	var disabledClick = false;
	var anispeed = 700;
	var pageNum = 1;
	var pageMax = 2;
	var $contHold1 = $("#contentholder1");
	var $contHold2 = $("#contentholder2");
	var slideShowOn = true;
	var itemsPerPageIndex = 6 + 1; //+1 for zero basing from page num
	var slideshowSpeed = 5000;
	
	//Initial method call to display content for page one
	ajxilLoadContent($contHold1, pageNum, itemsPerPageIndex);
	
	//Event listener for right navigation button
	$('#rightNav').click(function(){
		slideShowOn = false;
		changePage(true);
	});
	
	//Event listener for left navigation button
	$('#leftNav').click(function(){
		slideShowOn = false;
		changePage(false);
	});
	
	//Event listener for lightBox blank area to close
	$('#lightBoxBG').click(function(){
		$('#lightBoxBG, #lightBoxContent').css({'display':'none'});
	});
	
	$('#loginLightBoxBG').click(function(){
		$('#login-box-modular').css({'display':'none'});
	});
	
	//Keypress listener on ESC to remove modular window
	document.onkeydown = function(evt) {
	    evt = evt || window.event;
	    var isEscape = false;
	    if ("key" in evt) {
	        isEscape = (evt.key == "Escape" || evt.key == "Esc");
	    } else {
	        isEscape = (evt.keyCode == 27);
	    }
	    if (isEscape) {
	    	$('#lightBoxBG, #lightBoxContent').css({'display':'none'});
	    	//Only referenced in slideshow but needs accessibility on non index pages.
	    	$('#login-box-modular').css({'display':'none'});
	    }
	};

	//Method to control which content holder is delegated to updated with setable direction of switching
	//#---Rather large and complex method, consider splitting where possible---#
	function changePage(next){
		if (!next){ //Next refers to direction (true = right, false = left slide)
			if (!disabledClick && pageNum >1){
				pageNum--;
				disabledClick = true; //Click is disabled to prevent spam clicking (bad UX)
				if($contHold1.css('display') == 'none'){ //Logic to determine correct contentHandler based on css properties
					$.when(ajxilLoadContent($contHold1, pageNum, itemsPerPageIndex)).then(function(){ //Ajax call using deferred method to force data to load before being displayed
						slideContRight($contHold2, $contHold1)});
				} else if($contHold2.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold2, pageNum, itemsPerPageIndex)).then(function(){
						slideContRight($contHold1, $contHold2)});
				} else {
					$contHold1.css({display: 'block'}); //Error state
					$contHold2.css({display: 'none'});
				}
				setTimeout(function(){disabledClick = false;},anispeed); //Delays reactivation of click until animation has completed
			} else { //Else block for when page is one to circulate to the maximum page number
				pageNum = pageMax;
				if($contHold1.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold1, pageMax, itemsPerPageIndex)).then(function(){
						slideContRight($contHold2, $contHold1)});
				} else if($contHold2.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold2, pageMax, itemsPerPageIndex)).then(function(){
						slideContRight($contHold1, $contHold2)});
				}
			}
		} else {
			if (!disabledClick && pageNum < pageMax){
				pageNum++;
				disabledClick = true;
				if($contHold1.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold1, pageNum, itemsPerPageIndex)).then(function(){
					slideContLeft($contHold2, $contHold1)});
				} else if($contHold2.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold2, pageNum, itemsPerPageIndex)).then(function(){
					slideContLeft($contHold1, $contHold2)});
				} else {
					$contHold1.css({display: 'block'});
					$contHold2.css({display: 'none'});
				} 
				setTimeout(function(){disabledClick = false;},anispeed);
			} else {
				pageNum = 1;
				if($contHold1.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold1, 1, itemsPerPageIndex)).then(function(){
						slideContLeft($contHold2, $contHold1)});
				} else if($contHold2.css('display') == 'none'){
					$.when(ajxilLoadContent($contHold2, 1, itemsPerPageIndex)).then(function(){
						slideContLeft($contHold1, $contHold2)});
				}
			}
		}
	}
	
	//Animation effect to slide content left
	function slideContLeft(shownDiv, hiddenDiv){
		shownDiv.animate({left: '-100%'},anispeed);
		hiddenDiv.css({display: 'block', left: '100%'});
		hiddenDiv.animate({left: '0'}, anispeed);
		setTimeout(function(){shownDiv.css({display: 'none'})},anispeed);
	}
	
	//Animation effect to slide content right
	function slideContRight(shownDiv, hiddenDiv){
		shownDiv.animate({left: '100%'},anispeed);
		hiddenDiv.css({display: 'block', left: '-100%'});
		hiddenDiv.animate({left: '0'}, anispeed);
		setTimeout(function(){shownDiv.css({display: 'none'})},anispeed);
	}
	
	//UI builder for data retrieved from JSON calls - Called from ajax method
	function setContentHolderContent(contentHolder,data) {
		contentHolder.empty();
		$.each(data.items,function(a,b){
			$div = $('<div />',{'data-itemID': b.id, 'class':'twoInColumnBox', click: function(){openLight(b.id);}});
			$div.append(
					'<img src="' + b.imgURL + '">' +
						'<div class="title">' +
						b.title + '</div>' +
						'<div class="description">' +
						b.description + '</div>' +
						'<div class="price">£' +
						Number(b.price).toFixed(2) + '</div>'
			);
			contentHolder.append($div);
		});
	}
	
	//AJAX call with return for deferred methods
	function ajxilLoadContent(contentHolder, pageNum, itemsPerPage){
		return $.ajax({
			url: 'rest/json/items?pageNo=' + pageNum + "&viewCount=" + itemsPerPage,
			contentType: "application/json",
		    dataType: "json",
			success: function(data) {
				setContentHolderContent(contentHolder,data);
				pageMax = data.pageMax;
			},
			error: function(e) {
				console.log("Error : " + JSON.stringify(e));
			}
		});
	}
	
	//SlideShow Loop (cancels when left or right Nav buttons are clicked)
	setTimeout(function loopslideShow(){
		if (pageNum != pageMax){
			changePage(true);
		} else {
			pageNum = 1;
			if($contHold1.css('display') == 'none'){
				$.when(ajxilLoadContent($contHold1, 1, itemsPerPageIndex)).then(function(){
					slideContLeft($contHold2, $contHold1)});
			} else if($contHold2.css('display') == 'none'){
				$.when(ajxilLoadContent($contHold2, 1, itemsPerPageIndex)).then(function(){
					slideContLeft($contHold1, $contHold2)});
			}
		}
	    if (slideShowOn) {
	    	setTimeout(loopslideShow, slideshowSpeed);
	    }
	}, slideshowSpeed);
	
	//LightBox Functions
	function openLight(itemID){
		$('#lightBoxBG').css({'display':'block'});
		slideShowOn = false;
		$.ajax({
			url: 'rest/json/items/' + itemID,
			contentType: "application/json",
		    dataType: "json",
			success: function(data) {
				buildLightBoxContent(data)
			},
			error: function(e) {
				console.log("Error : " + JSON.stringify(e));
			}
		});
	}
	
	//Populate lightBox Content
	function buildLightBoxContent(data){
		var $lightBoxCont = $('#lightBoxContent');
		$lightBoxCont.empty();
		$lightBoxCont.append('<div class=imgCont><img src="' + data.imgURL +'"/></div>'); //Image
		$lightBoxCont.append('<div class="title">' + data.title +'</div>');	//Title
		$lightBoxCont.append('<div class="price">£' + Number(data.price).toFixed(2) +'</div>'); //Price
		$lightBoxCont.append('<div class="available">Left in Stock: ' + data.unitsAvailable +'</div>'); //Items in stock
		$lightBoxCont.append('<div class="review"><div class="reviewScore">User Rating: ' + data.avgScore 
				+ '/10</div><div class="reviewCont">('+ data.reviewCount +')</div></div>'); //Review Stats
		$lightBoxCont.append('<form method="get" action="addProduct"> <input type="hidden" name="id" value="' + data.id
				+'" /> <input type="hidden" name="price" value="' + data.price +'" /> <button>Add To Basket</button></form>'); //Add to basket
		$lightBoxCont.append('<div class="description">' + data.description +'</div>'); //Add description
		$lightBoxCont.append('<form method="get" action="productPage"> <input type="hidden" name="id" value="' + data.id
				 + '" /> <button>See Product Details</button></form>'); //Go To Page
		if ($('#lightBoxBG').css('display') != 'none'){
			$lightBoxCont.css({'display':'block'});
		}
	}
});