<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Exception detected</title>
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
                <h1>Exception detected</h1>
            </div>
        </div>
        <div id="failed" class="alert alert-danger alert-dismissible"><i class="fa fa-exclamation-circle"></i>
            <button type="button" class="close" data-dismiss="alert" onclick="document.getElementById('failed').style.display = 'none'">&times;</button>
            <h4>Exception occurred while processing the request</h4>
            <%
                String exception = pageContext.getException().getClass().toString();
                String message = pageContext.getException().getMessage();
            %>

            <p>Request from " ${pageContext.errorData.requestURI} " is failed! </p>
            <p>Servlet: ${pageContext.errorData.servletName}</p>
            <p>Status code: ${pageContext.errorData.statusCode}</p>
            <p>Type: <%=exception%>
            </p>
            <p>Message: <%=message%>
            </p>
        </div>
    </div>
</div>
</body>
</html>
