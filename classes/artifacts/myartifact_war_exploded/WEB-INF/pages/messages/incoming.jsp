<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="Refresh" content="30" />
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
      <form class="navbar-form navbar-right" action="${rootURL}search" method="post">
        <div class="form-group">
          <input type="text" class="form-control" name="pattern" placeholder="Search...">
        </div>
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

      <c:if test="${empty messages}">
        <thead>
        <h4><p class="bg-primary"> You have not received any messages yet </p></h4>
        </thead>
      </c:if>

      <h2 class="sub-header">Incoming messages</h2>

        <div class="table-responsive">
          <form class="form-inline" action="${rootURL}delete" method="post">
          <table class="table table-striped">
            <colgroup>
              <col span="1" style="width: 8%;">
              <col span="1" style="width: 15%;">
              <col span="1" style="width: 17%;">
              <col span="1" style="width: 35%;">
              <col span="1" style="width: 15%;">

            </colgroup>
            <thead>
            <tr>
              <td>
                <button type="submit" class="btn btn-primary">
                  <span class="glyphicon glyphicon-trash" aria-hidden="true"> </span>
                  Delete
                </button>
              </td>
              <td><b></b></td>
              <td><b></b></td>
              <td><b></b></td>
              <td><b></b></td>
            </tr>
            </thead>
            <c:if test="${messages != null}">
            <c:forEach items="${messages}" var="message">
              <tbody>
              <tr>
                <td>
                  <input type = "checkbox" name = "messageIds" value = "${message.id}" /> <c:out value = " " />
                </td>
                <td> <a href="${rootURL}view?id=${message.id}"> ${message.sender.getUsername()}
                </td>
                <td>${message.summary}</td>
                <td>${message.messageText}</td>
                <td>${message.date}</td>
                <td>
                </td>
              </tr>
              </tbody>
            </c:forEach>
            </c:if>
          </table>
          </form>
        </div>
    </div>
  </div>
</div>

</body>
</html>