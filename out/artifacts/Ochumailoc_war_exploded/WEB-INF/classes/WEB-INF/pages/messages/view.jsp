<%--
  Created by IntelliJ IDEA.
  User: elena
  Date: 10/22/15
  Time: 11:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<div class="container">
  <div class="header clearfix">
    <nav class="navbar navbar-default navbar-static-top">
      <a class="navbar-brand" href="${rootURL}contacts">OChuMail</a>
      <div class="container">
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="${rootURL}incoming"><strong> Mailbox </strong></a></li>
            <li><a href="${rootURL}contacts">Contacts</a></li>
            <form class="navbar-form navbar-right" action='${pageContext.request.contextPath}logout' method='POST'>
              <button type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-user" aria-hidden="true"> </span>
                Log out as <sec:authentication property="principal.username"/>
              </button>
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
  </div>
  <div class="jumbotron">

    <dl class="dl-horizontal">
      <dt>From</dt>
      <dd> ${message.sender.getUsername()} </dd>
    </dl>
    <dl class="dl-horizontal">
      <dt>To</dt>
      <dd> ${message.receiver.getUsername()} </dd>
    </dl>

    <dl class="dl-horizontal">
      <dt>Summary</dt>
      <dd> ${message.summary}  </dd>
    </dl>

    <dl class="dl-horizontal">
      <dt>Message</dt>
      <dd> ${message.messageText}  </dd>
    </dl>

    <dl class="dl-horizontal">
      <dt>Date</dt>
      <dd> ${message.date} </dd>
    </dl>

    <dl class="dl-horizontal">
      <dt>
      <form class="form-inline" action="${rootURL}reply?id=${message.id}" method='GET'>
        <button type="submit" class="btn btn-success" name="id" value="${message.id}">
          <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span>
          Reply
        </button>
      </form>
      </dt>
    </dl>

  </div>
</div>
</body>
</html>