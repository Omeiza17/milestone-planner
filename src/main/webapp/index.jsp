<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/main.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="/" class="navbar-brand">Milestone</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="nav navbar-nav">
            <li class="nav-item m-2"><a href="/dashboard">Dashboard</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li class="nav-item"><a href="/login">Login</a></li>
        </ul>
    </div>
</nav>

<div class="container m-5">
    <div class="row">
        <aside class="col-3">
            <img class="avatar" src="https://picsum.photos/2048/1365/?random">
            <h1>Welcome </h1>
            <a class="btn" href="/milestone" onclick="">Add Project</a>
        </aside>
        <main class="col-9">
            <div class="row">
                <section class="col-6">

                <c:forEach items="${projectList}" var="project">
                        <a href="project/?projectName=${project.getId()}" class="project card">
                            <p><c:out value="${project.getId()}"/></p>
                            <a href="project/"></a>
                        </a>
                </c:forEach>
                </section>
                <section class="col-6">

                    <c:forEach items="${projectList}" var="project">
                        <div class="project card">
                            <p><c:out value="${project.getId()}"/></p>
                            <a href="project/"></a>
                        </div>
                    </c:forEach>
                </section>
            </div>
        </main>
    </div>
</div>
</body>
</html>
