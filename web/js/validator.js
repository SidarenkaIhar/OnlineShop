/**
 * Checks the user name against the specified pattern
 *
 * @param name  name to check
 * @returns {boolean}   returns true if the name matches the pattern and false otherwise
 */
function isNameCorrect(name, elementId) {
    var namePattern = /^[A-Za-z0-9_]{4,128}$/;
    var isValid = namePattern.test(name);
    output = '';
    if (!isValid) {
        output = 'The Name must be between 4 and 128 characters and contain only [A-Za-z0-9_]';
    }
    showError(elementId, output);
    return isValid;
}

/**
 * Checks the user password against the specified pattern
 *
 * @param password  password to check
 * @param confirm   confirm password
 * @returns {boolean}   returns true if the password matches the pattern and false otherwise
 */
function isPasswordCorrect(password, confirm, passwordElementId, confirmElementId) {
    var passwordPattern = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z@#$%]).{6,128})/;
    var isValid = passwordPattern.test(password);
    var outputPassword = '';
    var outputConfirm = '';
    if (!isValid) {
        outputPassword = 'The Password must be between 6 and 128 characters and contain ' +
            'only [A-Za-z0-9@#$%] ( min one "0-9", one "a-z", one "A-Z@#$%")';
    }
    if (isValid && password !== confirm) {
        outputConfirm = 'Password and password confirmation do not match!';
        isValid = false;
    }
    showError(passwordElementId, outputPassword);
    showError(confirmElementId, outputConfirm);
    return isValid;
}

/**
 * Checks the user mail against the specified pattern
 *
 * @param mail  mail to check
 * @returns {boolean}   returns true if the mail matches the pattern and false otherwise
 */
function isMailCorrect(mail, elementId) {
    var mailPattern = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,128})$/;
    var isValid = mailPattern.test(mail);
    output = '';
    if (!isValid) {
        output = 'The E-Mail Address does not appear to be valid!';
    }
    showError(elementId, output);
    return isValid;
}

/**
 * Checks the number against the specified pattern
 *
 * @param number    number to check
 * @param elementId item ID to display the error
 * @returns {boolean}   returns true if the number matches the pattern and false otherwise
 */
function isInteger(number, elementId) {
    var intPattern = /^\d{1,10}$/;
    var isValid = intPattern.test(number);
    output = '';
    if (!isValid) {
        output = 'The field must be between 1 and 10 digits and contain only [0-9]';
    }
    showError(elementId, output);
    return isValid;
}

/**
 * Checks the float number against the specified pattern
 *
 * @param number    float number to check
 * @param elementId item ID to display the error
 * @returns {boolean}   returns true if the float number matches the pattern and false otherwise
 */
function isFloat(number, elementId) {
    var floatPattern = /^\d{1,11}(\.\d{1,4})?$/;
    var isValid = floatPattern.test(number);
    output = '';
    if (!isValid) {
        output = 'The field must be between 1 and 10 digits (and max 4 digits after the decimal point) and contain only [0-9][.]';
    }
    showError(elementId, output);
    return isValid;

}

/**
 * Checks the string against the specified pattern
 *
 * @param string    string to check
 * @param elementId item ID to display the error
 * @returns {boolean}   returns true if the string matches the pattern and false otherwise
 */
function isString(string, elementId) {
    var namePattern = /^[A-Za-z0-9 ]{3,128}$/;
    var isValid = namePattern.test(string);
    output = '';
    if (!isValid) {
        output = 'The field must be between 3 and 128 characters and contain only [A-Za-z0-9 ]';
    }
    showError(elementId, output);
    return isValid;

}

/**
 * Checks the url against the specified pattern
 *
 * @param url    url to check
 * @param elementId item ID to display the error
 * @returns {boolean}   returns true if the url matches the pattern and false otherwise
 */
function isUrl(url, elementId) {
    var urlPattern = /^[A-Za-z0-9/:._]{0,128}$/;
    var isValid = urlPattern.test(url);
    output = '';
    if (!isValid) {
        output = 'The url must be between 3 and 128 characters and contain only [A-Za-z0-9/:._]';
    }
    showError(elementId, output);
    return isValid;

}

/**
 * Displays an error message on the page
 *
 * @param elementId item ID to display the error
 * @param message   error text to display
 */
function showError(elementId, message) {
    document.getElementById(elementId + '_error').innerHTML = message;
}
