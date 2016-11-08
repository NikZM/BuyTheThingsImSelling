<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling - ${catPageName}</title>
</head>
<body>
	<jsp:include page="templates/navbar.jsp" />
<body>
<div class="listOfItems">
<c:forEach items="${itemListObj}" var="itemList">
	<div class="itemInList">
		<div class="itemIMG"><img src="<c:out value="${itemList.imgURL}" />"/></div>
		<div class="itemTitle"><c:out value="${itemList.title}" /></div>
		<div class="itemPrice"><c:out value="${itemList.price}" /></div>
		<div class="itemReviewInfo">
			<div class="itemReviewScore"><c:out value="${itemList.avgReviewScore}" />/10</div>
			<div class="itemReviewCount">(<c:out value="${itemList.reviewCount}" />)</div>
		</div>
		<a href="${pageContext.request.contextPath}/productPage?id=<c:out value="${itemList.id}"/>" >
			<button>See Product Details</button>
		</a>
	</div>
</c:forEach>
</div>
	<jsp:include page="templates/footer.jsp" />
</body>
</html>