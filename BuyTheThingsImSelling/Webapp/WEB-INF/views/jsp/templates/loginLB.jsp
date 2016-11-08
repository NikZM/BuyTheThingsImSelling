<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<div id="login-box-modular" style="display:none;">
	<div id="loginLightBoxBG"></div>
	<div id="loginLightBoxCont">
		<form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
			<h3>Email</h3>
			<input type='text' name="username">
			<h3>Password</h3>
			<input type='password' name='password' />
			<input class="loginSubmitButton" name="submit" type="submit" value="LOG IN" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
		<div id="registrationLink"><h6>OR</h6><a href="${pageContext.request.contextPath}/register">REGISTER</a></div>
	</div>
</div>