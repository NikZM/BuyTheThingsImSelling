<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="utf-8" %>
<script type="text/javascript">
 	$( document ).ready(function(){
        $('#login').click(function(e){
			e.preventDefault();
			$('#login-box-modular').css({'display':'block'});
			$('[name="username"]').focus();
       	});
     });

	function getContextPath() {
  		return "${pageContext.request.contextPath}";
	}
	
	function getBasketItems(){
		return ${cart.toString()};
	}
</script>
<div class="page-wrap">
<nav id='navigation'>
	<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/img/bttislogo.PNG" /></a>
	<ul>
		<li><a class="navLink" href="${pageContext.request.contextPath}/">HOME</a></li>
		<li><a class="navLink" id="catButton" href="${pageContext.request.contextPath}/categories">CATEGORIES</a>
			<div id="catBox" style="display:none;"></div>
		</li>
		<c:if test="${pageContext.request.userPrincipal.name == null}">
			<li><a class="navLink" href="login" id="login">SIGN IN</a></li>
		</c:if>
		<sec:authorize access="hasRole('2') and isAuthenticated()">
			<li><a class="navLink" href="${pageContext.request.contextPath}/sell" >SELL</a></li>
		</sec:authorize>
		<sec:authorize access="hasRole('3') and isAuthenticated()">
			<li><a class="navLink" href="${pageContext.request.contextPath}/admin" >ADMIN</a></li>
		</sec:authorize>
		<c:if test="${pageContext.request.userPrincipal.name != null}"> 
			<li><a class="navLink" href="${pageContext.request.contextPath}/account" >ACCOUNT</a></li>
			<li><a class="navLink" href="javascript:formSubmit()">LOGOUT</a>
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
				<form action="${logoutUrl}" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
				</form>
				<script>
					function formSubmit() {
						document.getElementById("logoutForm").submit();
					}
				</script>
			</li>
		</c:if>
		<li>
			<a class="navLink" href="basket" id="basketButton">BASKET (${cart.getPriceTotalS()})</a>
			<div id="basketBox" style="display:none;"></div>
		</li>
	</ul>
</nav>
<jsp:include page="loginLB.jsp" />