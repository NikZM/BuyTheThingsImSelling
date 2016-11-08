<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling - Seller</title>
</head>
<body>
	<jsp:include page="templates/navbar.jsp" />
	<div id="sellerPane">
		<div id="manageItemsList">
		<c:forEach items="${prevItems}" var="prevItem">
			<div class="sellersItem">
				<div class="itemIMG"><img src="<c:out value="${prevItem.imgURL}" />"/></div>
				<div class="itemTitle"><c:out value="${prevItem.title}" /></div>
				<div class="itemPrice"><c:out value="${prevItem.price}" /></div>
				<div class="itemReviewInfo">
					<div class="itemReviewScore"><c:out value="${prevItem.avgReviewScore}" />/10</div>
					<div class="itemReviewCount">(<c:out value="${prevItem.reviewCount}" />)</div>
				</div>
				<a href="${pageContext.request.contextPath}/productPage?id=<c:out value="${prevItem.id}"/>" >
					<button>See Product Details</button>
				</a>
				<a href="${pageContext.request.contextPath}/sell/newItem?id=<c:out value="${prevItem.id}"/>" >
					<button>Edit</button>
				</a>
			</div>
		</c:forEach>
		</div>
		<div id="listNew">OR <a href="${pageContext.request.contextPath}/sell/newItem">List New Item</a></div>
	</div>
		<jsp:include page="templates/footer.jsp" />
</body>
</html>