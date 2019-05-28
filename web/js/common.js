function editUser(userId) {
    var form = document.getElementById("form-edituser");
    var id = document.getElementById("user-id");
    id.value=userId;
    form.submit();
}
