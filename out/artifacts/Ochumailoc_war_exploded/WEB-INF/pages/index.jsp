<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
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
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${rootURL}contacts">OChuMail</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <form class="navbar-form navbar-right" action='${pageContext.request.contextPath}logout' method='POST'>
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"> </span>
                            Log out as <sec:authentication property="principal.username"/>
                        </button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <li><a href="${rootURL}incoming"><strong>Mailbox</strong></a></li>
                </c:if>
            </ul>

        </div>
    </div>
</nav>



    <div class="jumbotron">
        <h2> Welcome, <sec:authentication property="principal.username"/>! </h2>
        <h4> Enjoy yourself with OChuMail! </h4>

    </div>
</div>
</body>
</html>
