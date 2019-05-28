<%@ page import="com.epam.training.onlineshop.entity.user.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of users</title>
    <script src="${pageContext.request.contextPath}/js/common.js"></script>
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
                    <a href="${pageContext.request.contextPath}/edituser" data-toggle="tooltip" title="Add new user" class="btn btn-primary"><i class="fa fa-plus"></i></a>
                    <button type="button" data-toggle="tooltip" title="Remove selected users" class="btn btn-danger"
                            onclick="confirm('Do you really want to delete the selected users?') ? document.getElementById('form-deleteuser').submit() : false;"><i class="fa fa-trash-o"></i></button>
                </div>
                <h1>List of users of the online shop</h1>
            </div>
        </div>
        <div id="failed" class="alert alert-danger alert-dismissible <%=request.getAttribute("failed")==null?"hidden":""%>"><i class="fa fa-exclamation-circle"></i><%=request.getAttribute("failed")%>
            <button type="button" class="close" data-dismiss="alert" onclick="document.getElementById('failed').style.display = 'none'">&times;</button>
        </div>
        <div id="success" class="alert alert-success alert-dismissible <%=request.getAttribute("success")==null?"hidden":""%>"><i class="fa fa-check-circle"></i><%=request.getAttribute("success")%>
            <button type="button" class="close" data-dismiss="alert" onclick="document.getElementById('success').style.display = 'none'">&times;</button>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-list"></i>Editable users</h3>
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <form action="${pageContext.request.contextPath}/" method="post" id="form-deleteuser">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <td class="text-center"> Select</td>
                                <td class="text-left"> User id</td>
                                <td class="text-left"> User group</td>
                                <td class="text-left"> User name</td>
                                <td class="text-left"> Password</td>
                                <td class="text-left"> Email</td>
                                <td class="text-left"> Is active</td>
                                <td class="text-left"> Date added</td>
                                <td class="text-center"> Action</td>
                            </tr>
                            </thead>
                            <tbody>
                            <% List<User> users = (List<User>) request.getAttribute("allUsers");
                                if (users != null && users.size() > 0) {
                                    for (User user : users) { %>
                            <tr>
                                <td class="text-center"><input type="checkbox" name="selected[]" value="<%=user.getId()%>"/></td>
                                <td class="text-left"><%=user.getId()%>
                                </td>
                                <td class="text-left"><%=user.getGroup().toString()%>
                                </td>
                                <td class="text-left"><%=user.getName()%>
                                </td>
                                <td class="text-left"><%=user.getPassword()%>
                                </td>
                                <td class="text-left"><%=user.getEmail()%>
                                </td>
                                <td class="text-left"><%=user.isEnabled()%>
                                </td>
                                <td class="text-left"><%=user.getCreationDate()%>
                                </td>
                                <td class="text-center">
                                    <button type="button" data-toggle="tooltip" title="Edit user" class="btn btn-primary" onclick="editUser(<%=user.getId()%>)"><i class="fa fa-pencil"></i></button>
                                </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr>
                                <td class="text-center" colspan="9">Users not found in the database.</td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </form>
                    <form action="${pageContext.request.contextPath}/edituser" method="post" id="form-edituser">
                        <input type="hidden" name="statement_type" value="SELECT" id="statement-type">
                        <input type="hidden" name="user_id" id="user-id">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
