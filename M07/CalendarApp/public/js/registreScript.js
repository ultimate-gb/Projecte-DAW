addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let registerButton = document.querySelector("#registerBtn");
    registerButton.addEventListener('click', function(ev) {
        ev.preventDefault();
        let valid = validarForm();
    })
}

function validarForm() {
    let email = document.querySelector("#emailInput").value;
    let nom = document.querySelector('#nomInput').value;
    let cognom1 = document.querySelector('#cognom1Input').value;
    let cognom2 = document.querySelector('#cognom2Input').value;
    let passwd = document.querySelector('#passInput').value;
    let dataNaix = document.querySelector('#dataNaixInput').value;
    let genere = document.querySelector('#genereSel').value;
    let telefon = document.querySelector('#telefonInput').value;
    let nacionalitat = document.querySelector('#nacionalitatSel');
    let nValids = 0;
    let telOptional = false;
    if(email.match(new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "g"))) {
        nValids++;
    }
    if(nom.match(new RegExp('^[A-Z][a-z]{1,40}$'))) {
        nValids++;
    }
    if(cognom1.match(new RegExp('^[A-Z][a-z]{1,100}$'))) {
        nValids++;
    }
    if(cognom2.match(new RegExp('^[A-Z][a-z]{0,100}$'))) {
        nValids++;
    }
    if(cognom1.length + cognom2.length <= 100) {
        nValids++;
    }
    if(passwd.match("^\\w+")) {
        nValids++;
    }
    if(dataNaix.length > 0) {
        nValids++;
    }
    let data = new Date(dataNaix);
    let today = new Date();
    if(data.getUTCFullYear() <= today.getUTCFullYear() && today.getUTCFullYear() - data.getUTCFullYear() >= 18) {
        nValids++;
    }
    if(genere != -1) {
        nValids++;
    }
    if(telefon.length == 0) {
        telOptional = true;
    }
    if(telefon.length > 0 && telefon.match(new RegExp('^[1-9][0-9]{8}$'))) {
        nValids++;
    }
    if(nacionalitat != -1) {
        nValids++;
    }
    if((nValids == 10 && telOptional == true) || (nValids == 11 && telOptional == false)) {
        f_ferPeticioAjax("POST", "http://localhost/projecte/CalendarApp/public/register/save", true, f_validar);
    }
}

function f_validar(data) {
    let resposta = tryParseJSONObject(data.responseText);
    if(resposta.status = 200) {
        // Enviar al login i notificar
    }
    else {
        // Mostrar alerta que no s'ha pogut registra
    }
}

function f_ferPeticioAjax(method, url, async, data, functToExecute) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            functToExecute(this);
        }
    };
    xmlhttp.open(method, url, async);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");        
    xmlhttp.send(data);
    return xmlhttp;
}

// Comprova si el json te un format valid i tambe comprova que si no peta retorni un objecte
function tryParseJSONObject (jsonString){
    try {
        let o = JSON.parse(jsonString);
        if (o && typeof o === "object") {
            return o;
        }
    }
    catch (e) { }

    return false;
};