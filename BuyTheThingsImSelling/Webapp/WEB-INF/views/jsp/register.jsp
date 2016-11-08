<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling - Register</title>
</head>
<body>
	<div class="page-wrap">
		<jsp:include page="templates/navbar.jsp" />
		<sf:form method="POST" modelAttribute="user" action="register/submit">  
			<table>  
			    <tbody>
				    <tr>  
				        <td><sf:label path="userName">Email:</sf:label></td>  
				        <td><sf:input path="userName" required="required" 
	       				placeholder="Email"
	       				type="email"
	       				name="email"
	       				pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
	       				title="Email should only contain lowercase letters. e.g. john@email.com"></sf:input></td>  
				    </tr>  
				    <tr>  
				        <td><sf:label path="passwordHashed">Password:</sf:label></td>  
				        <td><sf:input path="passwordHashed" 
				        type="password" 
				        required="required" 
				        placeholder="Password"></sf:input></td>
				        
				    </tr>  
				    <tr>  
				        <td><sf:label path="paypalAcc">paypalAcc</sf:label></td>  
				        <td><sf:input path="paypalAcc" 
				        required="required" 
				        placeholder="PayPal Account" 
				        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
				        title="Email should only contain lowercase letters. e.g. john@email.com"></sf:input></td>  
				    </tr>
				    <tr>  
				        <td colspan="2">  
				            <input type="submit" value="Submit">  
				        </td>  
				    </tr>  
				</tbody>
			</table>    
		</sf:form>  
	</div>
			<jsp:include page="templates/footer.jsp" />
</body>
</html>