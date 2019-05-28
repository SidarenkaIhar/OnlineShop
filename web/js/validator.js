function isNameCorrect(name) {
    var namePattern = /^[A-Za-z0-9_]{4,20}$/;
    var isValid = namePattern.test(name);
    output = '';
    if (!isValid) {
        output = 'Name must be between 4 and 20 characters and contain only "A-Z a-z 0-9 _"';
    }
    document.getElementById('name-error').innerHTML = output;
    return isValid;
}

function isPasswordCorrect(password, confirm) {
    var passwordPattern = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z@#$%]).{6,20})/;
    var isValid = passwordPattern.test(password);
    var outputPassword = '';
    var outputConfirm = '';
    if (!isValid) {
        outputPassword = 'Password must be between 6 and 20 characters and contain only "a-z 0-9 A-Z @#$%" ( one "0-9", one "a-z", one "A-Z @#$%")';
    }
    if (isValid && password !== confirm) {
        outputConfirm = 'Password and password confirmation do not match!';
        isValid = false;
    }
    document.getElementById('password-error').innerHTML = outputPassword;
    document.getElementById('confirm-error').innerHTML = outputConfirm;
    return isValid;
}

function isMailCorrect(mail) {
    var mailPattern = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
    var isValid = mailPattern.test(mail);
    output = '';
    if (!isValid) {
        output = 'E-Mail Address does not appear to be valid!';
    }
    document.getElementById('mail-error').innerHTML = output;
    return isValid;
}
