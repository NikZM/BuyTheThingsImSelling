<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" %> 
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling - ${productTitle}</title>
</head>
<body>
	<jsp:include page="templates/navbar.jsp" />
	<div class="productPage">
		<img src="${item.imgURL}"/>
		<div class="productTitle">${item.title}</div>
		<div class="productPrice"><fmt:formatNumber value="${item.price}"  type="currency"/></div>
		<div class="productAvailability">${item.unitsAvailable} left in stock</div>
		<div class="productBasketAdd">
			<form method="get" action="addProduct"> 
				<input type="hidden" name="id" value="${item.id}"/> 
				<input type="hidden" name="price" value="${item.price}" /> 
				<button>Add To Basket</button>
			</form>
		</div>
		<div class="productDescription">
			<div class="descriptionTitle">Description</div>
			<div class="descriptionBody">${item.description}</div>
		</div>
		<sec:authorize access="hasRole('1') and isAuthenticated()">
			<jsp:include page="templates/reviewForm.jsp" />
		</sec:authorize>
		<div class="productReviewTitle">Reviews</div>
		<div class="itemReviews">
		<c:if test="${empty item.reviews}">
			<div class="noReviewPrompt">There are currently no reviews for this item.</div>
		</c:if>
		<c:forEach items="${item.reviews}" var="reviewList">
			<c:out value="${reviewList.userEmail}" /><br />
			<c:out value="${reviewList.comment}" />
		</c:forEach>
		</div>
	</div>
	<script>
	$('.productPage img').click(function(){
		viewImgFS($(this));
	})
	function viewImgFS($img){
		//Create lightBox for image
	}
	</script>
		<jsp:include page="templates/footer.jsp" />
</body>
</html>