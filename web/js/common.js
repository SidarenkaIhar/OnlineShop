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
    var parsedRequest = JSON.parse(request);
    for (var key in parsedRequest) {
        var value = parsedRequest[key];
        switch (key) {
            case "userLogin":
                document.getElementById("userLogin").innerHTML = getLocalizedText('Welcome ', 'Привет ') + value;
                break;
            case "showAdminMenu":
                if (value === true) {
                    document.getElementById("adminMenu").style.display = 'block';
                }
                break;
            case "productCatalog":
                showProductCatalog(value);
                break;
            case "entitiesToShow":
                showEntities(value);
                break;
            case "userCart":
                showEntities(value);
                break;
            case "editableEntity":
                editableEntity(value);
                break;
            case "messageSuccess":
            case "messageFailed":
                showMessage(key, value.trim());
                break;
            case "typeOperation":
                document.getElementById("typeOperation").value = value;
                break;
            case "userGroups":
                setUserGroups(value);
                break;
            case "categories":
                setCategories(value);
                break;
            case "orderStatuses":
                setOrderStatuses(value);
                break;
        }
    }
}

/**
 * Displays the online store product catalog
 *
 * @param products  the online store product catalog
 */
function showProductCatalog(products) {
    var output = '';
    for (var product in products) {
        var prod = products[product];
        var image = prod.image === '' ? '/image/noimage.png' : prod.image;
        output += '<div class="product-layout col-lg-3 col-md-3 col-sm-6 col-xs-12"><div class="product-thumb transition">' +
            '<div class="image"><img src="' + image + '" alt="' + prod.name + '" title="' + prod.name + '" class="img-responsive"></div>' +
            '<div class="caption"><h4>' + prod.name + '</h4><p>' + prod.description.substring(0, 200) + '...</p><p class="price">$' + prod.price + '</p></div>' +
            '<div class="button-group"><button type="button" onclick="addToCart(' + prod.id + ',\'' + prod.name + '\',' + prod.price + ');"><i class="fa fa-shopping-cart"></i> ' +
            '<span class="hidden-xs hidden-sm hidden-md">' + getLocalizedText("Add to Cart", "Добавить в корзину") + '</span></button></div></div></div>'
    }
    if (output.length === 0) {
        output = getLocalizedText('Nothing found on your request.', 'Ничего не найдено.');
    }
    document.getElementById("productCatalog").innerHTML = output;

}

/**
 * Displays all entities of the store
 *
 * @param entities the entities of the store
 */
function showEntities(entities) {
    var output = '';
    for (var ent in entities) {
        var entity = entities[ent];
        output += '<tr><td class="text-center"><input type="checkbox" name="selected[]" value="' + entity.id + '"/></td>';
        for (var key in entity) {
            var value = entity[key];
            if (!containId(key)) {
                if (key === "password") {
                    value = value.join("");
                } else if (key === "image") {
                    value = value === '' ? '/image/noimage.png' : value;
                    value = '<img src="' + value + '" class="img-responsive admin">';
                }
                output += '<td class="text-left">' + value + '</td>';
            }
        }
        output += '<td class="text-center"><button type="button" data-toggle="tooltip" title="' + getLocalizedText("Edit", "Редактировать") +
            '" class="btn btn-primary" onclick=editEntity("' + entity.id + '")><i class="fa fa-pencil"></i></button></td></tr>';
    }
    if (output.length === 0) {
        output = '<tr><td class="text-center" colspan="20">' + getLocalizedText('Nothing found on your request.', 'Ничего не найдено.') + '</td></tr>';
    }
    document.getElementById("allEntitiesList").innerHTML = output;
}

/**
 * Checks the string for the content of the word "ID"
 *
 * @param string    string to check
 * @returns {boolean}   returns true if the string contains the word "ID"
 */
function containId(string) {
    return string.toUpperCase().includes("ID", string.length - 2);
}

/**
 * Displays all of the user group store
 *
 * @param userGroups    the user group store
 */
function setUserGroups(userGroups) {
    var output = '<select name="group" id="entity_group" class="form-control">';
    for (var group in userGroups) {
        output += '<option value="' + userGroups[group] + '">' + userGroups[group] + '</option>';
    }
    output += '</select>';
    document.getElementById("userGroups").innerHTML = output;
}

/**
 * Displays all categories of the store
 *
 * @param categories    categories of the store
 */
function setCategories(categories) {
    var output = '<select name="categoryId" id="entity_categoryId" class="form-control">';
    for (var category in categories) {
        output += '<option value="' + categories[category].id + '">' + categories[category].name + '</option>';
    }
    output += '</select>';
    document.getElementById("categories").innerHTML = output;
}

/**
 * Displays all order statuses of the store
 *
 * @param statuses    statuses of the store
 */
function setOrderStatuses(statuses) {
    var output = '<select name="orderStatus" id="entity_orderStatus" class="form-control">';
    for (var status in statuses) {
        output += '<option value="' + statuses[status] + '">' + statuses[status] + '</option>';
    }
    output += '</select>';
    document.getElementById("orderStatus").innerHTML = output;
}

/**
 * Displays the settings of the currently edited entity
 *
 * @param entity  the currently edited entity
 */
function editableEntity(entity) {
    for (var field in entity) {
        var value = entity[field];
        document.getElementById('entity_' + field).value = value;
        switch (field) {
            case "id":
                if (value > 0) {
                    document.getElementById("welcome_message").innerHTML = getLocalizedText("Edit entity #", "Редактирование элемента №") + value;
                }
                break;
            case "password":
                document.getElementById("entity_password").value = value.join("");
                document.getElementById("entity_confirm").value = value.join("");
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
        window.scrollTo(0, 0);
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
 * Removes all marked entities
 */
function deleteEntities(servet) {
    var entities = [];
    var formDelete = document.getElementById("form-deleteEntity");
    for (var i = 0; i < formDelete.elements.length; i++) {
        if (formDelete.elements[i].name === "selected[]" && formDelete.elements[i].checked) {
            entities.push(formDelete.elements[i].value);
        }
    }
    var entitiesToEdit = {"entitiesToEdit": entities, "typeOperation": "DELETE"};
    postServletJSON(servet, entitiesToEdit);
}

/**
 * Sends to the edit entity page
 *
 * @param entityId    entity id
 */
function editEntity(entityId) {
    document.getElementById("entitiesToEdit").value = entityId;
    var form = document.getElementById("form-editEntity");
    form.submit();
}

/**
 * Processes and applies the parameters passed to the page
 */
function applyingParameters(servlet) {
    setCurrentTime();
    var param = getAllUrlParams();
    if (param.typeOperation === "SELECT" && param.entitiesToEdit && param.entitiesToEdit >= 0) {
        getEntityToEdit(param.entitiesToEdit, servlet);
    } else {
        postServletJSON(servlet);
    }
}

/**
 * Sets the current time when new items are added
 */
function setCurrentTime() {
    var creationDate = document.getElementById("entity_creationDate");
    var dateStatusChange = document.getElementById("entity_dateStatusChange");
    if (creationDate) {
        creationDate.value = new Date().toISOString();
    }
    if (dateStatusChange) {
        dateStatusChange.value = new Date().toISOString();
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
 * Get entity from database for editing
 *
 * @param entityId    entity id
 * @param servlet   servlet url
 */
function getEntityToEdit(entityId, servlet) {
    var entity = [entityId];
    var entitiesToEdit = {"entitiesToEdit": entity, "typeOperation": "SELECT"};
    postServletJSON(servlet, entitiesToEdit);
}

/**
 * Save entity data to the database
 * @param servlet   servlet url
 */
function saveEntity(servlet) {
    var entity = {};
    var typeOperation = document.getElementById("typeOperation").value;
    var entityForm = document.getElementById("form-entity");
    for (var i = 0; i < entityForm.elements.length; i++) {
        var element = entityForm.elements[i];
        if (element.id.includes("entity_")) {
            if ((element.name === "string" && !isString(element.value, element.id)) ||
                (element.name === "float" && !isFloat(element.value, element.id)) ||
                (element.name === "integer" && !isInteger(element.value, element.id)) ||
                (element.name === "url" && !isUrl(element.value, element.id)) ||
                (element.name === "name" && !isNameCorrect(element.value, element.id)) ||
                (element.name === "email" && !isMailCorrect(element.value, element.id))) {
                return;
            }
            if (element.name === "password") {
                var confirmPassword = document.getElementById('entity_confirm').value;
                if (!isPasswordCorrect(element.value, confirmPassword, element.id, 'entity_confirm')) {
                    return;
                }
                entity[element.name] = element.value.split('');
                continue;
            }
            entity[element.id.replace("entity_", "")] = element.value.trim();
        }
    }
    var editableEntity = {"editableEntity": entity, "typeOperation": typeOperation};
    postServletJSON(servlet, editableEntity);
}

/**
 * Adds products from the catalog to the shopping cart
 *
 * @param productId     product id to add
 * @param productName   product name
 * @param productPrice  product price to add
 */
function addToCart(productId, productName, productPrice) {
    var userId = document.getElementById("user_id").value;
    var cart = {"userId": userId, "productName": productName, "productId": productId, "productPrice": productPrice, "productQuantity": 1};
    var shoppingCart = {"shoppingCart": cart, "typeOperation": "INSERT"};
    postServletJSON('/homeServlet', shoppingCart);
}

/**
 * Returns text based on the user's language
 *
 * @param enText    English text
 * @param ruText    Russian text
 * @returns {*}     text based on the user's language
 */
function getLocalizedText(enText, ruText) {
    var userLanguage = document.getElementById("userLanguage").value;
    if (userLanguage) {
        return userLanguage === "ru_RU" ? ruText : enText;
    }
    return enText;
}

/**
 * Changes the user language to the specified language
 *
 * @param language  target language
 */
function changeLanguage(language) {
    var currentLanguage = document.getElementById("userLanguage").value;
    if (language !== currentLanguage) {
        var userLanguage = {"userLanguage": language, "typeOperation": "UPDATE"};
        postServletJSON('/homeServlet', userLanguage);
        if (language === "en_US") {
            window.location.href = '/';
        } else {
            window.location.href = '/html/ru/index.html';
        }
    }
}