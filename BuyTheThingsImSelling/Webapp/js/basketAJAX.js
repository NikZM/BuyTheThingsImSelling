$( document ).ready(function(){  
	
	var basketItems = {};
	
	//Ajax Call the retrieves the basket items by ID
	function ajaxBasketItemCall(basketItemString, outputMethod){
		console.log(JSON.stringify(basketItemString));
		return $.ajax({
			url: getContextPath() + '/rest/json/basket',
			contentType: "application/json",
		    dataType: "json",
		    data : {
		    	ids: basketItemString
		    },
			success: function(data) {
				console.log("loading data with resp: " + JSON.stringify(data));
				outputMethod(data);
			},
			error: function(e) {
				console.log("Error: Failed To Load Basket");
			}
		});
	}
	
	var createBasketList = function (basketData){
		$basketBox = $('#basketBox');
		$basketBox.empty();
		$.each(basketData.items, function(a,b){
			var $basketItem = $('<div class="basketItem" />');
			$basketBox.append($basketItem);
				$basketItem.append('<img src="' + b.imgURL + '" />');
				$basketItem.append('<div class="title">' + b.title + '</div>');
				$basketItem.append('<div class="price">PRICE: £' + Number(b.price).toFixed(2) + '</div>');
				var amount = findAmountByID(b.id);
				$basketItem.append('<div class="amount">QUANTITY: ' + amount + '</div>');
				$basketItem.append('<form method="get" action="removeProduct"> <input type="hidden" name="id" value="' + b.id
						+'" /><button>Remove</button></form>');
		});
		$basketBox.append('<a href="' + getContextPath() + '/basket"><button>Check Out</button></a>');
	}
	
	//Event Listener
	$('#basketButton').click(function(e){
		e.preventDefault();
		if($('#basketBox').is(':visible')){
			$('#basketBox').hide();
		} else {
			basketItems = getBasketItems();
			var basketItemString = convertBasketItemToIDString();
			$.when(ajaxBasketItemCall(basketItemString, createBasketList)).then(function(){ $('#basketBox').show();});
		}
	});
	
	function convertBasketItemToIDString(){
		var arr = [];
		$.each(basketItems,function(a,b){
			arr.push(b.itemID);
		});
		return arr.join(",");
	}
	
	function findAmountByID(id){
		var val = 1;
		$.each(basketItems,function(a,b){
			if (b.itemID == id){
				val = b.amount;
			}
		});
		return val;
	}

});