
<%@taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:url var="rootURL" value="/"/>

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
        <c:if test="${empty pageContext.request.userPrincipal}">
          <li><a href="${rootURL}signup">Sign up</a></li>
          <li><a href="${rootURL}contacts">Contacts</a></li>
        </c:if>
        <c:if test="${not empty pageContext.request.userPrincipal}">
          <li><a href="${rootURL}contacts">Contacts</a></li>
          <form class="navbar-form navbar-right" action='${rootURL}logout' method='POST'>
            <button type="submit" class="btn btn-primary">
              <span class="glyphicon glyphicon-user" aria-hidden="true"> </span>
              Log out as <sec:authentication property="principal.username"/>
            </button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          </form>
        </c:if>
      </ul>

    </div>
  </div>
</nav>




  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-9 col-sm-offset-1 col-md-10 col-md-offset-1 main">


        <div class="row placeholders">
          <div class="col-xs-6 col-sm-3 placeholder">
          </div>
          <div class="col-xs-6 col-sm-3 placeholder">
          </div>
          <div class="col-xs-6 col-sm-3 placeholder">
          </div>
          <div class="col-xs-6 col-sm-3 placeholder">
          </div>
        </div>
        <p class="inner cover">
        <h3 class="cover-heading"></h3>
        <p class="col-md-6 col-md-offset-2">
          <c:if test="${param.logout != null}">
        <div class="alert alert-success">
          You have logged out.
        </div>
        </c:if>
        <c:if test="${param.error != null}">
          <div class="alert alert-danger">
            Invalid Username and Password.
          </div>
        </c:if>
        </p>
        <div class="jumbotron">
          <c:if test="${pageContext.request.userPrincipal == null}">
            <form:form id="loginForm" method="post" action="${rootURL}login" modelAttribute="user"
                       class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">
              <div class="form-group">
                <label for="username" class="col-sm-2 control-label">UserName*</label>
                <div class="col-sm-4">
                  <input type="text" id="username" name="username" class="form-control" placeholder="Username" />
                </div>
              </div>
              <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password*</label>
                <div class="col-sm-4">
                  <input type="password" id="password" name="password" class="form-control" placeholder="Password" />
                </div>
              </div>
              <div class="input-group input-sm">
                <div class="checkbox">
                  <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-4">
                  <input type="submit" class="btn btn-primary" value="Login">
                </div>
              </div>
            </form:form>
          </c:if>
          <c:if test="${not empty pageContext.request.userPrincipal}">

            <h2> Welcome, <sec:authentication property="principal.username"/> </h2>


            <h5> Enjoy yourself with OChuMail! </h5>
            <p>
              <a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}incoming" role="button">Mailbox</a>
            </p>

          </c:if>
        </div>
      </div>
      </div>
</body>
</html>
