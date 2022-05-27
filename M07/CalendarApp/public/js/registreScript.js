addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let registerButton = document.querySelector("#registerBtn");
    registerButton.addEventListener('click', function(ev) {
        ev.preventDefault();
        validarForm();
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
    let nacionalitat = document.querySelector('#nacionalitatSel').value;
    let nValids = 0;
    let telOptional = false;
    if(email.match(new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "g"))) {
        document.querySelector("#emailL").classList.remove("errorInputs");
        document.querySelector("#emailInput").classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#emailL").classList.add("errorInputs");
        document.querySelector("#emailInput").classList.add("errorInputs");
    }
    if(nom.length <= 40) {
        document.querySelector("#nomL").classList.remove("errorInputs");
        document.querySelector('#nomInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#nomL").classList.add("errorInputs");
        document.querySelector('#nomInput').classList.add("errorInputs"); 
    }
    if(nom.match(new RegExp('^[A-Z][a-z]+\\s?[A-Z]?[a-z]*$'))) {
        document.querySelector("#nomL").classList.remove("errorInputs");
        document.querySelector('#nomInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#nomL").classList.add("errorInputs");
        document.querySelector('#nomInput').classList.add("errorInputs");
    }
    if(cognom1.match(new RegExp('^[A-Z][a-z]{1,100}$'))) {
        document.querySelector("#cognom1L").classList.remove("errorInputs");
        document.querySelector('#cognom1Input').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#cognom1L").classList.add("errorInputs");
        document.querySelector('#cognom1Input').classList.add("errorInputs");
    }
    if(cognom2.match(new RegExp('^[A-Z][a-z]{0,100}$'))) {
        document.querySelector("#cognom2L").classList.remove("errorInputs");
        document.querySelector('#cognom2Input').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#cognom2L").classList.add("errorInputs");
        document.querySelector('#cognom2Input').classList.add("errorInputs");
    }
    if(cognom1.length + cognom2.length <= 100) {
        document.querySelector("#passwdL").classList.remove("errorInputs");
        document.querySelector('#passInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#passwdL").classList.add("errorInputs");
        document.querySelector('#passInput').classList.add("errorInputs");
    }
    if(passwd.match("^\\w+")) {
        document.querySelector("#passwdL").classList.remove("errorInputs");
        document.querySelector('#passInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#passwdL").classList.add("errorInputs");
        document.querySelector('#passInput').classList.add("errorInputs");
    }
    if(dataNaix.length > 0) {
        document.querySelector("#dataNaixL").classList.remove("errorInputs");
        document.querySelector('#dataNaixInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#dataNaixL").classList.add("errorInputs");
        document.querySelector('#dataNaixInput').classList.add("errorInputs");
    }
    let data = new Date(dataNaix);
    let today = new Date();
    if(data.getUTCFullYear() <= today.getUTCFullYear() && today.getUTCFullYear() - data.getUTCFullYear() >= 18) {
        document.querySelector("#dataNaixL").classList.remove("errorInputs");
        document.querySelector('#dataNaixInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#dataNaixL").classList.add("errorInputs");
        document.querySelector('#dataNaixInput').classList.add("errorInputs");
    }
    if(genere != -1) {
        document.querySelector('#genereL').classList.remove("errorInputs");
        document.querySelector('#genereSel').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector('#genereL').classList.add("errorInputs");
        document.querySelector('#genereSel').classList.add("errorInputs");
    }
    if(telefon.length == 0) {
        document.querySelector('#telefonL').classList.remove("errorInputs");
        document.querySelector('#telefonInput').classList.remove("errorInputs");
        telOptional = true;
    }
    if(telefon.length > 0 && telefon.match(new RegExp('^[1-9][0-9]{8}$'))) {
        document.querySelector('#telefonL').classList.remove("errorInputs");
        document.querySelector('#telefonInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        if(telOptional == false) {
            document.querySelector('#telefonL').classList.add("errorInputs");
            document.querySelector('#telefonInput').classList.add("errorInputs");
        }
    }
    if(nacionalitat != -1) {
        document.querySelector('#nacL').classList.remove("errorInputs");
        document.querySelector('#nacionalitatSel').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector('#nacL').classList.add("errorInputs");
        document.querySelector('#nacionalitatSel').classList.add("errorInputs");
    }
    if((nValids == 11 && telOptional == true) || (nValids == 12 && telOptional == false)) {
        f_ferPeticioAjax("POST", "http://localhost:8081/projecte/CalendarApp/public/register/save", true, generarLiniaDeParametres({email:email,nom:nom,cognom1:cognom1,cognom2:cognom2,pass:passwd, genere:genere,telefon:telefon,nacionalitat,nacionalitat, dataNaix:dataNaix}),f_validar);
    }
}

function f_validar(data) {
    let resposta = tryParseJSONObject(data.responseText);
    if(resposta.status = 200) {
        // Enviar al login i notificar
        console.log(resposta);
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

function generarLiniaDeParametres(data) {
    let txt = "";
    let i = 0;
    for(let key in data) {
        txt += key + "="+data[key];
        if(i < data.length) {
            txt += "&";
        }
        i++;
    }
    return txt;
}