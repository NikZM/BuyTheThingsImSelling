<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />

<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling - BASKET</title>
</head>
<body>
	<jsp:include page="templates/navbar.jsp" />
	<div id="checkOutItems">
		<c:forEach items="${basketItems}" var="basketItem" varStatus="pStatus">
			<div class="checkOutItem">
				<div class="itemIMG"><img src="<c:out value="${basketItem.imgURL}" />"/></div>
				<div class="itemTitle"><c:out value="${basketItem.title}" /></div>
				<div class="itemPrice"><c:out value="${basketItem.price}" /></div>
				<div class="itemQuant"><c:out value="${basketItem.unitsAvailable}" /></div>
		 		<form method="GET" action="basket/update">
					<input type="hidden" name="price" value = "${basketItem.price}"/>
					<input type="hidden"name="id" value = "${basketItem.id}"/>
			       <input name="quantity" type="number" required="required" value="${basketItem.unitsAvailable}" />
			        <input type="submit" value="Update">  
				</form> 
			</div>
		</c:forEach>
		</div>
		
		<form id="myContainer" method="post" action="/checkout"></form>
		<script src="//www.paypalobjects.com/api/checkout.js" async></script>
			<script>
			window.paypalCheckoutReady = function() {
			    paypal.checkout.setup('SLPGE8UFTEJNS', {
			        environment: 'sandbox',
			        container: 'myContainer'
			    });
			};
			</script>
		<jsp:include page="templates/footer.jsp" />
</body>
</html>