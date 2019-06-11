/**
 *  Sends a post request to the servlet
 *
 * @param srvUrl    servlet url
 * @param dataSend  json data to send
 */
function postServletJSON(srvUrl, dataSend) {
    var xhrequest = new XMLHttpRequest();
    xhrequest.open("POST", srvUrl, true);
    xhrequest.onreadystatechange = reqReadyStateChange;
    xhrequest.send(JSON.stringify(dataSend));

    function reqReadyStateChange() {
        if (xhrequest.readyState === 4) {
            var status = xhrequest.status;
            if (status === 200) {
                parseJSON(xhrequest.responseText)
            }
        }
    }
}

/**
 * Processes the response from the server
 *
 * @param request   the response from the server
 */
function parseJSON(request) {
    var req = JSON.parse(request);
    for (var val in req) {
        switch (val) {
            case "usersToShow":
                showUsers(req[val]);
                break;
            case "userGroups":
                setUserGroups(req[val]);
                break;
            case "editableUser":
                editableUser(req[val]);
                break;
            case "messageSuccess":
            case "messageFailed":
                showMessage(val, req[val].trim());
                break;
            case "typeOperation":
                document.getElementById("user_typeOperation").value = req[val];
                break;
        }
    }
}

/**
 * Displays all users of the store
 *
 * @param users the users of the store
 */
function showUsers(users) {
    var output = '';
    for (var user in users) {
        output += '<tr>';
        output += '<td class="text-center"><input type="checkbox" name="selected[]" value="' + users[user].id + '"/></td>';
        for (var key in users[user]) {
            output += '<td class="text-left" id="' + key + '">';
            if (key === "password") {
                output += users[user][key].join("");
            } else {
                output += users[user][key];
            }
            output += '</td>';
        }
        output += '<td class="text-center"><button type="button" data-toggle="tooltip" title="Edit user" class="btn btn-primary" ' +
            'onclick=editUser("' + users[user].id + '")><i class="fa fa-pencil"></i></button></td></tr>';
    }
    if (output.length > 0) {
        document.getElementById("allUsersList").innerHTML = output;
    }
}

/**
 * Displays all of the user group store
 *
 * @param userGroups    the user group store
 */
function setUserGroups(userGroups) {
    var output = '<select name="group" id="user_group" class="form-control">';
    for (var group in userGroups) {
        output += '<option value="' + userGroups[group] + '">' + userGroups[group] + '</option>';
    }
    output += '</select>';
    if (document.getElementById("userGroups")) {
        document.getElementById("userGroups").innerHTML = output;
    }
}

/**
 * Displays the settings of the currently edited user
 *
 * @param user  the currently edited user
 */
function editableUser(user) {
    for (var field in user) {
        document.getElementById("user_" + field).value = user[field];
        switch (field) {
            case "id":
                if (user[field] > 0) {
                    document.getElementById("welcome_message").innerHTML = "Edit user №" + user[field];
                }
                break;
            case "password":
                document.getElementById("user_password").value = user[field].join("");
                document.getElementById("confirm_password").value = user[field].join("");
                break;
        }
    }
}

/**
 * Displays messages from the server
 *
 * @param messageId message id
 * @param text  message text
 */
function showMessage(messageId, text) {
    if (text.length > 0) {
        document.getElementById(messageId).style.display = 'block';
        document.getElementById(messageId + 'Text').innerHTML = text;
    } else {
        hideMessage(messageId);
    }
}

/**
 * Hides the message from the user
 *
 * @param messageId message id
 */
function hideMessage(messageId) {
    if (messageId.length > 0) {
        document.getElementById(messageId).style.display = 'none';
    }
}

/**
 * Removes all marked users
 */
function deleteUsers() {
    var users = [];
    var formDelete = document.getElementById("form-deleteuser");
    for (var i = 0; i < formDelete.elements.length; i++) {
        if (formDelete.elements[i].name === "selected[]" && formDelete.elements[i].checked) {
            users.push(formDelete.elements[i].value);
        }
    }
    var usersToEdit = {"usersToEdit": users, "typeOperation": "DELETE"};
    postServletJSON("/homeServlet", usersToEdit);
}

/**
 * Sends to the edit user page
 *
 * @param userId    user id
 */
function editUser(userId) {
    document.getElementById("user_id").value = userId;
    var form = document.getElementById("form-edituser");
    form.submit();
}

/**
 * Processes and applies the parameters passed to the page
 */
function applyingParameters() {
    var param = getAllUrlParams();
    if (param.typeOperation === "SELECT" && param.usersToEdit && param.usersToEdit >= 0) {
        getUserToEdit(param.usersToEdit);
    } else {
        postServletJSON('/editUserServlet');
    }
}

/**
 * Processes and applies parameters passed to the page via get request
 *
 * @param url   address with a get request
 */
function getAllUrlParams(url) {
    var queryString = url ? url.split('?')[1] : window.location.search.slice(1);
    var obj = {};
    if (queryString) {
        queryString = queryString.split('#')[0];
        var arr = queryString.split('&');

        for (var i = 0; i < arr.length; i++) {
            var a = arr[i].split('=');
            var paramNum = undefined;
            var paramName = a[0].replace(/\[\d*\]/, function (v) {
                paramNum = v.slice(1, -1);
                return '';
            });
            var paramValue = typeof(a[1]) === 'undefined' ? true : a[1];
            if (obj[paramName]) {
                if (typeof obj[paramName] === 'string') {
                    obj[paramName] = [obj[paramName]];
                }
                if (typeof paramNum === 'undefined') {
                    obj[paramName].push(paramValue);
                } else {
                    obj[paramName][paramNum] = paramValue;
                }
            }
            else {
                obj[paramName] = paramValue;
            }
        }
    }
    return obj;
}

/**
 * Get user from database for editing
 *
 * @param userId    user id
 */
function getUserToEdit(userId) {
    var user = [userId];
    var usersToEdit = {"usersToEdit": user, "typeOperation": "SELECT"};
    postServletJSON("/editUserServlet", usersToEdit);
}

/**
 * Save user data to the database
 */
function saveUser() {
    var userName = document.getElementById('user_name').value;
    var userPassword = document.getElementById('user_password').value;
    var confirmPassword = document.getElementById('confirm_password').value;
    var userEmail = document.getElementById('user_email').value;
    if (isNameCorrect(userName) && isPasswordCorrect(userPassword, confirmPassword) && isMailCorrect(userEmail)) {
        var user = {};
        var typeOperation = document.getElementById("user_typeOperation").value;
        var userForm = document.getElementById("form-user");
        for (var i = 0; i < userForm.elements.length; i++) {
            if (userForm.elements[i].id.includes("user_")) {
                if (userForm.elements[i].name === "password") {
                    user[userForm.elements[i].name] = userForm.elements[i].value.split('');
                    continue;
                }
                user[userForm.elements[i].name] = userForm.elements[i].value;
            }
        }
        var editableUser = {"editableUser": user, "typeOperation": typeOperation};
        postServletJSON("/editUserServlet", editableUser);
    }
}

