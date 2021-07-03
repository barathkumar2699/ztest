<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navs" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navs">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/ztest/create">Create User</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/ztest/Login">View User<span class="sr-only">(current)</span></a>
      </li>
      
      <li class="nav-item">
        <a class="nav-link" href="/ztest/Logout">Logout</a>
      </li>
      <li class="nav-item">
        <form class="nav-link" action="/ztest/search" method="post">
        <input type="text" name="username"> 
        <input type="submit" value="Search"> 
        </form>
      </li>
    </ul>
    
  </div>
</nav>

<table class="table">
  <thead>
    <tr>
      
      <th scope="col">Id</th>
      <th scope="col">username</th>
      <th scope="col">password</th>
      <th scope="col">Email</th>
      <th scope="col">Phone</th>
      <th scope="col">address</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var = "user" items="${users }" >

    <tr>
      <td>${user.id }</td>
      <td>${user.username }</td>
      <td>${user.password }</td>
       <td>${user.email }</td>
        <td>${user.phone }</td>
         <td>${user.address }</td>
      <td>
      
      <button type="button" data-toggle="modal" data-target="#myModal${user.id}" >Edit</button></form></td>
      <!-- Modal -->
<div class="modal fade" id="myModal${user.id }" role="dialog">  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Edit User</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      
	<form class="form" action="/ztest/edited" method="post">
<div class="form-group">
    <label for="id">Id</label>
    <input type="text" class="form-control" name="id" aria-describedby="emailHelp"
     placeholder="Enter id" value="${user.id }">
    
  </div>
    <div class="form-group">
    <label for="email">Phone</label>
    <input type="email" class="form-control" name="email" aria-describedby="emailHelp"
     placeholder="Enter id" value="${user.email }">
    
  </div>
<div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" name="username" aria-describedby="username"
     placeholder="Enter id" value="${user.username }">
    
  </div>
<div class="form-group">
    <label for="password">Password</label>
    <input type="text" class="form-control" name="password" aria-describedby="emailHelp"
     placeholder="Enter id" value="${user.password }">
    
  </div>
  <div class="form-group">
    <label for="phone">Phone</label>
    <input type="text" class="form-control" name="phone" aria-describedby="emailHelp"
     placeholder="Enter id" value="${user.phone }">
    
  </div>
    <div class="form-group">
    <label for="address">Address:</label>
    <input type="text" class="form-control" name="address" aria-describedby="emailHelp"
     placeholder="Enter id" value="${user.address }">
    
  </div>
<input type="submit" /> 
<div classname="text-danger">${error }</div>
</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
      <td>
      <form action="/ztest/delete" method="post">
      <input type="text" name="did" value="${user.id }" hidden="true"/>
      <button type="submit">Delete</button></form></td>
     
    </tr>
   </c:forEach>
  </tbody>
</table>


 <div class="text-danger">${userDeleted }</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>