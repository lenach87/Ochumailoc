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
  <style type="text/css">
    td { line-height: 1em;
      height: 1em;
      overflow: hidden;
      word-wrap: break-word;
      text-overflow: ellipsis;
      font-size: 15px;
      font-family: arial, sans-serif;
    }

    table {
      line-height: 1em;
      height: 1em;
      overflow: hidden;
      word-wrap: break-word;
      text-overflow: ellipsis;
      width: 100%;
      font-size: 15px;
      font-family: arial, sans-serif;}

    td.blue span, td.blue span a {
      display: block;
      height: 100%;
      width: 100%;

    }
    dl {
      font-size: 15px;
      font-family: arial, sans-serif;
    }
    dt {
      font-size: 15px;
      font-family: arial, sans-serif;
    }
    dd {
      font-size: 15px;
      font-family: arial, sans-serif;
    }

  </style>

    </head>
<body>
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
        <li><a href="${rootURL}contacts">Contacts</a></li>
        <form class="navbar-form navbar-right" action='${rootURL}logout' method='POST'>
          <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-user" aria-hidden="true"> </span>
            Log out as <sec:authentication property="principal.username"/>
          </button>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
      </ul>
      <form class="navbar-form navbar-right">
        <input type="text" class="form-control" placeholder="Search...">
      </form>
    </div>
  </div>
</nav>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul class="nav nav-sidebar">
        <li><a href="${rootURL}compose">Compose <span class="sr-only">(current)</span></a></li>
        <li class="active"><a href="${rootURL}incoming">Incoming</a></li>
        <li><a href="${rootURL}outgoing">Outgoing</a></li>
        <li><a href="${rootURL}deleted">Deleted</a></li>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">


      <h2 class="sub-header">Message</h2>


    <dl class="dl-horizontal">
      <dt>From</dt>
      <dd> ${message.sender.getUsername()} </dd>
      <dt>To</dt>
      <dd> ${message.receiver.getUsername()} </dd>
      <dt>Date</dt>
      <dd> ${message.date} </dd>
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
      <dt>
      <form class="form-inline" action="${rootURL}reply?id=${message.id}" method='GET'>
        <button type="submit" class="btn btn-success" name="id" value="${message.id}">
          <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"> </span>
          Reply
        </button>
      </form>
      </dt>
      <dd>
      <form class="form-inline" action='${rootURL}delete?id=${message.id}' method='POST'>
        <button type="submit" class="btn btn-primary" name="id" value="${message.id}">
          <span class="glyphicon glyphicon-trash" aria-hidden="true"> </span>
          Delete
        </button>
      </form>
      </dd>
    </dl>
</div>
  </div>
</div>
</body>
</html>