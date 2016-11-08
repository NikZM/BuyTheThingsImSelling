<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling</title>
</head>
<body>
	<jsp:include page="templates/navbar.jsp" />
	<sf:form method="POST" modelAttribute="itemEntity" action="postItem">  
		<table>  
		    <tbody>
		    	<sf:input path="id" type="hidden"/>
			    <tr>  
			        <td><sf:label path="title">Title:</sf:label></td>  
			        <td><sf:input path="title" required="required"></sf:input></td>  
			    </tr>  
			    <tr>  
			        <td><sf:label path="description">Description:</sf:label></td>  
			        <td><sf:textarea path="description"></sf:textarea></td>  
			    </tr>  
			    <tr>  
			        <td><sf:label path="price">Price:</sf:label></td>  
			        <td><sf:input path="price" pattern="\d+(\.\d{2})?" required="required"></sf:input></td>  
			    </tr>  
			    <tr>  
			        <td><sf:label path="imgURL">Image URL:</sf:label></td>  
			        <td><sf:input path="imgURL" pattern="https?://.+"></sf:input></td>  
			    </tr>
			    <tr>  
			        <td><sf:label path="unitsAvailable">UnitsAvailable:</sf:label></td>  
			        <td><sf:input path="unitsAvailable" type="number" required="required"></sf:input></td>  
			    </tr>  
			    <tr>  
			        <td><sf:label path="categories">Categories:</sf:label></td>  
			        <td>
			        	<sf:input id="categories" type="hidden" name="categories" path="categories"></sf:input>
			        	<div id="itemCatOptions"></div>
			        </td>  
			    </tr>  
			    <tr>  
			        <td colspan="2">  
			            <input type="submit" value="Submit">  
			        </td>  
			        <td></td>
			        <td></td>  
			    </tr>  
			</tbody>
		</table>    
	</sf:form>
	<c:if test="${itemEntity.id > 0 }"> 
		<form method="get" action="deleteItem"> 
			<input type="hidden" name="id" value="${itemEntity.id}"/> 
			<button id="delButton">DELETE</button>
		</form>
	</c:if>
		<jsp:include page="templates/footer.jsp" />
</body>
</html>