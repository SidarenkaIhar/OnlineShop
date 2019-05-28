<%@ page import="com.epam.training.onlineshop.users.UserGroup" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Editing user settings</title>
    <script src="${pageContext.request.contextPath}/js/validator.js"></script>
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
                    <button type="submit" form="form-customer" data-toggle="tooltip" title="Save" class="btn btn-primary"><i class="fa fa-save"></i></button>
                    <a href="${pageContext.request.contextPath}/edituser" data-toggle="tooltip" title="Clear" class="btn btn-default"><i class="fa fa-reply"></i></a>
                </div>
                <h1><%=request.getAttribute("editedUser")%>
                </h1>
            </div>
        </div>
        <div id="failed" class="alert alert-danger alert-dismissible <%=request.getAttribute("failed")==null?"hidden":""%>"><i class="fa fa-exclamation-circle"></i><%=request.getAttribute("failed")%>
            <button type="button" class="close" data-dismiss="alert" onclick="document.getElementById('failed').style.display = 'none'">&times;</button>
        </div>
        <div id="success" class="alert alert-success alert-dismissible <%=request.getAttribute("success")==null?"hidden":""%>"><i class="fa fa-check-circle"></i><%=request.getAttribute("success")%>
            <button type="button" class="close" data-dismiss="alert" onclick="document.getElementById('success').style.display = 'none'">&times;</button>
        </div>
        <div class="container-fluid">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="fa fa-pencil"></i>User data</h3>
                </div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/edituser" method="post" id="form-customer" class="form-horizontal"
                          onsubmit="return(isNameCorrect(document.getElementById('input-username').value) && isPasswordCorrect(document.getElementById('input-password').value, document.getElementById('input-confirm').value) && isMailCorrect(document.getElementById('input-email').value))">
                        <div class="col-sm-10">
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab-customer">
                                    <fieldset>
                                        <div class="form-group">
                                            <input type="hidden" name="statement_type" value="<%=request.getAttribute("val_statement_type")%>" id="statement-type">
                                            <input type="hidden" name="user_id" value="<%=request.getAttribute("val_user_id")%>" id="user-id">
                                            <label class="col-sm-2 control-label" for="input-customer-group">Group</label>
                                            <div class="col-sm-10">
                                                <select name="user_group" id="input-customer-group" class="form-control">
                                                    <% for (UserGroup group : UserGroup.values()) {
                                                        if (request.getAttribute("user_group_id") != null && group.getGroupId() == (int) request.getAttribute("user_group_id")) { %>
                                                    <option value="<%=group.getGroupId() %>" selected="selected"><%=group %>
                                                    </option>
                                                    <% } else { %>
                                                    <option value="<%=group.getGroupId() %>"><%=group %>
                                                    </option>
                                                    <% }
                                                    } %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="col-sm-2 control-label" for="input-username">Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="username" value="<%=request.getAttribute("val_username")%>" placeholder="Name" id="input-username" class="form-control"/>
                                                <div id="name-error" class="text-danger"></div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="col-sm-2 control-label" for="input-password">Password</label>
                                            <div class="col-sm-10">
                                                <input type="password" name="password" value="<%=request.getAttribute("val_password")%>" placeholder="Password" id="input-password" class="form-control" autocomplete="off"/>
                                                <div id="password-error" class="text-danger"></div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="col-sm-2 control-label" for="input-confirm">Confirm Password</label>
                                            <div class="col-sm-10">
                                                <input type="password" name="confirm" value="<%=request.getAttribute("val_confirm")%>" placeholder="Confirm Password" autocomplete="off" id="input-confirm" class="form-control"/>
                                                <div id="confirm-error" class="text-danger"></div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="col-sm-2 control-label" for="input-email">E-Mail</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="email" value="<%=request.getAttribute("val_email")%>" placeholder="E-Mail" id="input-email" class="form-control"/>
                                                <div id="mail-error" class="text-danger"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label" for="input-status">Status</label>
                                            <div class="col-sm-10">
                                                <select name="status" id="input-status" class="form-control">
                                                    <% if (request.getAttribute("status") != null && (boolean) request.getAttribute("status")) { %>
                                                    <option value="1" selected="selected">Enabled</option>
                                                    <option value="0">Disabled</option>
                                                    <% } else { %>
                                                    <option value="1">Enabled</option>
                                                    <option value="0" selected="selected">Disabled</option>
                                                    <% } %>
                                                </select>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>