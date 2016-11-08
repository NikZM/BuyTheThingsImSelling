<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="templates/standardHeader.jsp" />
<script src="${pageContext.request.contextPath}/js/noSlideshowLogin.js"></script>
<title>Buy The Things Im Selling - ADMIN</title>
<style>
#queryArea textarea{
	width: 80%;
    height: 150px;
    margin: auto;
    display: block;
    padding: 5px;
    font-size: 1.1em;
}

#queryArea input[type="button"]{
	font-size: 0.9em;
    padding: 3px;
    margin: 10px auto;
    display: block;
    position: relative;
    top: -24px;
}

#errorReport {
	display: none;
    background: #FFD4D4;
    font-size: 0.9em;
    padding: 10px
}

#resultSet {
	width: 100%;
    overflow: auto;
    background: #FFEDCD;
    border: 1px solid black;
}

#resultSet th{
	text-align: left;
    font-weight: 700;
    padding: 4px;
    border: 1px solid black;
}

#resultSet td{
	padding: 6px;
	border: 1px solid black;
}

</style>
<script>
		function ajaxPost(){
			var dataForm = $('#queryArea').serialize();
			$.ajax({
				url:'${pageContext.request.contextPath}/rest/json/admin/sqlReturn',
				data: dataForm,
				success:function(resp){
					createTable(resp);
				},
				error:function(error){
					$('#errorReport').empty();
					$('#errorReport').show();
					$('#errorReport').append(error.responseText);
				}
			});
		}
		
		function createTable(resp){
			var $table = $('#resultSet');
			$table.empty();
			$table.append('<tr>');
			$.each(resp.headers,function(a,b){
				$table.append('<th>' + b + '</th>');
			});
			$table.append('</tr>');
			$.each(resp.rows,function(a,b){
				$table.append('<tr>');
				$.each(b,function(a2,b2){
					$table.append('<td>' + b2 + '</td>');
				});
				$table.append('</tr>');
			});
		}
	</script>
</head>
<body>
	<jsp:include page="templates/navbar.jsp" />
	<form id="queryArea"method="POST" action="postItem">
		<textarea name="query"></textarea>
		<input type="button" name="type" value="query" onClick="ajaxPost()" />
	</form>
	<div id="errorReport"></div>
	<table id="resultSet">
	</table>
		<jsp:include page="templates/footer.jsp" />
</body>
</html>