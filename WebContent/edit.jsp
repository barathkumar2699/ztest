<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form class="form" action="/ztest/edited" method="post">
Id:
<input classname="" type="text" value="${user.id }" name="id" />
Username:
<input classname="" type="text" name="username" value=${user.username } />
Password:
<input type="text" name="password" value="${user.password }" />
<input type="submit" /> 
<div classname="text-danger">${error }</div>
</form>
</body>
</html>