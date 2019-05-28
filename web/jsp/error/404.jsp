<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page not found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome/css/font-awesome.min.css" type="text/css">
</head>
<body>
<div id="container">
    <div id="content">
        <div class="page-header">
            <div class="container-fluid">
                <div class="pull-right">
                    <a href="${pageContext.request.contextPath}/" data-toggle="tooltip" title="Home" class="btn btn-primary"><i class="fa fa-home"></i></a>
                </div>
                <h1>Page not found</h1>
            </div>
        </div>
        <div id="failed" class="alert alert-danger alert-dismissible"><i class="fa fa-exclamation-circle"></i><h4>The page you are looking for is not found on the server!</h4>
            <button type="button" class="close" data-dismiss="alert" onclick="document.getElementById('failed').style.display = 'none'">&times;</button>
        </div>
    </div>
</div>
</body>
</html>
