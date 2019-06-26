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
            case "userLogin":
                document.getElementById("userLogin").innerHTML = 'Welcome ' + req[val];
                break;
            case "showAdminMenu":
                if (req[val] === true) {
                    document.getElementById("adminMenu").style.display = 'block';
                }
                break;
            case "productCatalog":
                showProductCatalog(req[val]);
                break;
            case "entitiesToShow":
                showEntities(req[val]);
                break;
            case "userCart":
                showEntities(req[val]);
                break;
            case "namesProducts":
                setNamesProducts(req[val]);
                break;
            case "editableEntity":
                editableEntity(req[val]);
                break;
            case "messageSuccess":
            case "messageFailed":
                showMessage(val, req[val].trim());
                break;
            case "typeOperation":
                document.getElementById("typeOperation").value = req[val];
                break;
            case "userGroups":
                setUserGroups(req[val]);
                break;
            case "categories":
                setCategories(req[val]);
                break;
            case "orderStatuses":
                setOrderStatuses(req[val]);
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
            '<div class="button-group"><button type="button" onclick="addToCart(' + prod.id + ',' + prod.price + ');"><i class="fa fa-shopping-cart"></i> ' +
            '<span class="hidden-xs hidden-sm hidden-md">Add to Cart</span></button></div></div></div>'
    }
    if (output.length === 0) {
        output = 'Nothing found on your request.';
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
    for (var entity in entities) {
        var ent = entities[entity];
        output += '<tr><td class="text-center"><input type="checkbox" name="selected[]" value="' + ent.id + '"/></td>';
        for (var key in ent) {
            if (key === "productId") {
                output += '<td class="text-left" id="entity_' + key + ent[key] + '">';
            } else {
                output += '<td class="text-left" id="entity_' + key + '">';
            }
            if (key === "password") {
                output += ent[key].join("");
            } else {
                output += ent[key];
            }
            output += '</td>';
        }
        output += '<td class="text-center"><button type="button" data-toggle="tooltip" title="Edit" class="btn btn-primary" ' +
            'onclick=editEntity("' + ent.id + '")><i class="fa fa-pencil"></i></button></td></tr>';
    }
    if (output.length === 0) {
        output = '<tr><td class="text-center" colspan="20">Nothing found on your request.</td></tr>';
    }
    document.getElementById("allEntitiesList").innerHTML = output;
}

/**
 * Displays the names of products in the shopping cart
 *
 * @param products  product names to display
 */
function setNamesProducts(products) {
    for (var product in products) {
        var productId = document.getElementById('entity_productId' + products[product].id);
        if (productId) {
            productId.innerHTML = products[product].name;
        }
    }
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
        document.getElementById('entity_' + field).value = entity[field];
        switch (field) {
            case "id":
                if (entity[field] > 0) {
                    document.getElementById("welcome_message").innerHTML = "Edit entity №" + entity[field];
                }
                break;
            case "password":
                document.getElementById("entity_password").value = entity[field].join("");
                document.getElementById("entity_confirm").value = entity[field].join("");
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
 */
function getEntityToEdit(entityId, servlet) {
    var entity = [entityId];
    var entitiesToEdit = {"entitiesToEdit": entity, "typeOperation": "SELECT"};
    postServletJSON(servlet, entitiesToEdit);
}

/**
 * Save entity data to the database
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
 * @param productPrice  product price to add
 */
function addToCart(productId, productPrice) {
    var userId = document.getElementById("user_id").value;
    var cart = {"userId": userId, "productId": productId, "productPrice": productPrice, "productQuantity": 1};
    var shoppingCart = {"shoppingCart": cart, "typeOperation": "INSERT"};
    postServletJSON('/homeServlet', shoppingCart);
}