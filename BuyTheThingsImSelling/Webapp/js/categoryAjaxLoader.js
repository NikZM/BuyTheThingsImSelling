$( document ).ready(function(){  
	
	//Ajax Call the retrieve list of categories
	function ajaxCategoryCall(outputMethod){
		return $.ajax({
			url: getContextPath() + '/rest/json/categoriesList',
			contentType: "application/json",
		    dataType: "json",
			success: function(data) {
				outputMethod(data);
			},
			error: function(e) {
				console.log("Error: Failed To Load Categories");
			}
		});
	}
	
	//Copies value of elements with the selected class into the form
	function moveArrayToFormInput(){
		var $categoryFormInput = $('#categories');
		var $selectedItems = $('.selected');
		var catArray = [];
		$categoryFormInput.empty();
		$.each($selectedItems,function(a,b){
			catArray.push(b.innerHTML);
		});
		$categoryFormInput.prop("value", catArray.join(","));
	}
	
	var sellersPageCategorySelect = function(categoryList){
		var $itemCatOptions = $('#itemCatOptions');
		$.each(categoryList.categories,function(a,b){
			var $categorySelectDiv = $('<div class="itemCatOpt" />');
			$categorySelectDiv.append(b);
			$itemCatOptions.append($categorySelectDiv);
			$categorySelectDiv.click(function(){
				if($categorySelectDiv.hasClass("selected")){
					$categorySelectDiv.removeClass("selected");
				} else {
					$categorySelectDiv.addClass("selected");
				}
				moveArrayToFormInput();
			});
		});
	}
	
	var createCatList = function(categoryList){
		$catBox = $('#catBox');
		$catBox.empty();
		$catBox.append('<a class="catOption" href="' + getContextPath() + '/category/all">ALL CATEGORIES</a>');
		$.each(categoryList.categories, function(a,b){
			$catBox.append('<a class="catOption" href="' + getContextPath() + '/category/' + b + '">' + b + '</a>');
		});
	}
	
	//Event Listener
	$('#catButton').click(function(e){
		e.preventDefault();
		if($('#catBox').is(':visible')){
			$('#catBox').hide();
		} else {
			$.when(ajaxCategoryCall(createCatList)).then(function(){ $('#catBox').show();});
		}
	});
	
	//Set Category list when document is ready
	if($('#itemCatOptions').length > 0) {
		ajaxCategoryCall(sellersPageCategorySelect);
	}
	
});