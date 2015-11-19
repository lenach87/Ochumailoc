<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>OChuMail</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <link href="http://getbootstrap.com/examples/dashboard/dashboard.css" rel="stylesheet">
</head>
<body>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<div class="container">
    <div class="header clearfix">
        <nav class="navbar navbar-default navbar-static-top">
            <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="${rootURL}login">Login</a></li>
                        <li><a href="${rootURL}contacts">Contacts</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
        </nav>
    </div>

    <div class="form-group">
        <form:form action="signup" method="post" modelAttribute="userForm"
                   class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">

        <div class="form-group">
            <label for="firstName" class="col-sm-2 control-label">First name</label>
            <div class="col-sm-4">
                <form:input path="firstName" type="text" class="form-control" placeholder="First Name" autofocus="true"/>
                <form:errors path="firstName" cssStyle="color: #ff0000;"/>
            </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="col-sm-2 control-label">Last name</label>
            <div class="col-sm-4">
                <form:input path="lastName" type="text" class="form-control" placeholder="Last Name" />
                <form:errors path="lastName" cssStyle="color: #ff0000;"/>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">E-mail</label>
            <div class="col-sm-4">
                <form:input path="email" type="text" class="form-control" placeholder="E-mail" />
                <form:errors path="email" cssStyle="color: #ff0000;"/>
            </div>
        </div>
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Username</label>
            <div class="col-sm-4">
                <form:input path="username" type="text" class="form-control" placeholder="Username" />
                <form:errors path="username" cssStyle="color: #ff0000;"/>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-4">
                <form:input path="password" type="password" class="form-control" placeholder="Password"/>
                <form:errors path="password" cssStyle="color: #ff0000;"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-4">
                <button class="btn btn-lg btn-primary btn-block"  type="submit" value="/signup"> Sign up</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<%-- </div> <!-- /container -->

<form:form action="signup" method="post" commandName="userForm">


        <tr>
            <td>First Name:</td>
            <td><form:input path="firstName" type="text" class="form-control" placeholder="First Name"  autofocus="true"/></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><form:input path="lastName" type="text" class="form-control" placeholder="Last Name" /></td>
        </tr>
        <tr>
        <tr>
            <td>User Name:</td>
            <td><form:input path="username" type="text" class="form-control" placeholder="Username"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input path="password" type="password" class="form-control" placeholder="Password"/></td>
        </tr>
        <tr>
            <td>E-mail:</td>
            <td><form:input path="email" type="text" class="form-control" placeholder="Email" /></td>
        </tr>
            <td colspan="2" align="center"><input type="submit" value="signup" /></td>
        </tr>
    </table>

</form:form>

</div>
--%>
<%--      <label for="firstName" class="sr-only">First Name</label>
      <input type="text" id="firstName" class="form-control" placeholder="First Name"  autofocus>
      <label for="lastName" class="sr-only">Last Name</label>
      <input type="text" id="lastName" class="form-control" placeholder="Last Name" >
      <label for="email" class="sr-only">Email</label>
      <input type="email" id="email" class="form-control" placeholder="Email" >
      <label for="username" class="sr-only">Username</label>
      <input type="text" id="username" class="form-control" placeholder="Username" >
      <label for="password" class="sr-only">Password</label>
      <input type="password" id="password" class="form-control" placeholder="Password" >
      <div class="checkbox">
          <label>
              <input type="checkbox" value="remember-me"> Remember me
          </label>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit" value="Register">Sign up</button>

      --%>


<%--
<form:form method="POST" modelAttribute="userAdd">
    <table>
        <tr>
            <td> First Name :</td>
            <td><form:input path="firstName" /></td>
        </tr>
        <tr>
            <td> Last Name :</td>
            <td><form:input path="lastName" /></td>
        </tr>
        <tr>
            <td> Email :</td>
            <td><form:input path="email" /></td>
        </tr>
        <tr>
            <td> Username :</td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td>Password :</td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="save"></td>
        </tr>
    </table>
</form:form>
--%>