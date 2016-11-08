<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/slideshow.js"></script>
<title>Buy The Things Im Selling</title>
</head>
<body>
	<div class="page-wrap">
		<jsp:include page="templates/navbar.jsp" />
		<div id="dynamic">
			<div id="contentholder1"><div class="twoInColumnBox"></div></div>
			<div id="contentholder2"></div>
			<div id="leftNav"></div>
			<div id="rightNav"></div>
		</div>
		<div id="lightBoxBG"></div>
		<div id="lightBoxContent"></div>
	</div>
	<jsp:include page="templates/footer.jsp" />
</body>
</html>