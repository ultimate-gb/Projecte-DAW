addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let registerButton = document.querySelector("#registerBtn");
    registerButton.addEventListener('click', function(ev) {
        ev.preventDefault();
        validarForm();
    });
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
    let errors = new Array();
    if(email.match(new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "g"))) {
        document.querySelector("#emailL").classList.remove("errorInputs");
        document.querySelector("#emailInput").classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#emailL").classList.add("errorInputs");
        document.querySelector("#emailInput").classList.add("errorInputs");
        errors.push("Email: Format Email Invalid");
    }
    if(nom.length <= 40) {
        document.querySelector("#nomL").classList.remove("errorInputs");
        document.querySelector('#nomInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#nomL").classList.add("errorInputs");
        document.querySelector('#nomInput').classList.add("errorInputs"); 
        errors.push("Nom: Longitud del Nom massa llarga");
    }
    if(nom.match(new RegExp('^[A-Z][a-z]+\\s?[A-Z]?[a-z]*$'))) {
        document.querySelector("#nomL").classList.remove("errorInputs");
        document.querySelector('#nomInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#nomL").classList.add("errorInputs");
        document.querySelector('#nomInput').classList.add("errorInputs");
        errors.push("Nom: Format Nom Invalid. Hauria de començar amb lletra majuscula seguit de lletres minuscules");
    }
    if(cognom1.match(new RegExp('^[A-Z][a-z]{1,100}$'))) {
        document.querySelector("#cognom1L").classList.remove("errorInputs");
        document.querySelector('#cognom1Input').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#cognom1L").classList.add("errorInputs");
        document.querySelector('#cognom1Input').classList.add("errorInputs");
        errors.push("Primer Cognom: Format Primer Cognom Invalid. Hauria de començar amb lletra majuscula seguit de lletres minuscules");
    }
    if(cognom2.length == 0 || cognom2.match(new RegExp('^([A-Z][a-z]{0,100})?$'))) {
        document.querySelector("#cognom2L").classList.remove("errorInputs");
        document.querySelector('#cognom2Input').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#cognom2L").classList.add("errorInputs");
        document.querySelector('#cognom2Input').classList.add("errorInputs");
        errors.push("Segon Cognom: Format Segon Cognom Invalid. Hauria de començar amb lletra majuscula seguit de lletres minuscules");
    }
    if(cognom1.length + cognom2.length <= 100) {
        if(cognom1.length > 0 && cognom1.length <= 100) {
            document.querySelector("#cognom1L").classList.remove("errorInputs");
            document.querySelector('#cognom1Input').classList.remove("errorInputs");
        }
        if(cognom2.length == 0 || (cognom2.length > 0 && cognom2.length <=100)) {
            document.querySelector("#cognom2L").classList.remove("errorInputs");
            document.querySelector('#cognom2Input').classList.remove("errorInputs");
        }

        nValids++;
    }
    else {
        document.querySelector("#cognom1L").classList.add("errorInputs");
        document.querySelector('#cognom1Input').classList.add("errorInputs");
        document.querySelector("#cognom2L").classList.add("errorInputs");
        document.querySelector('#cognom2Input').classList.add("errorInputs");
        errors.push("Cognoms: La longitud del dos cognoms afegint un espai al mig no pot superar els 100 caracters de longitud.");
    }
    if(passwd.match("^\\w+")) {
        document.querySelector("#passwdL").classList.remove("errorInputs");
        document.querySelector('#passInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#passwdL").classList.add("errorInputs");
        document.querySelector('#passInput').classList.add("errorInputs");
        errors.push("Password: Format Password Invalid");
    }
    if(dataNaix.length > 0) {
        document.querySelector("#dataNaixL").classList.remove("errorInputs");
        document.querySelector('#dataNaixInput').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector("#dataNaixL").classList.add("errorInputs");
        document.querySelector('#dataNaixInput').classList.add("errorInputs");
        errors.push("Data Naixement: Camp obligatori");
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
        errors.push("Data Naixement: Per poder registrar-se ha de ser major de 18 anys o cumplir els 18 aquest any");
    }
    if(genere != -1) {
        document.querySelector('#genereL').classList.remove("errorInputs");
        document.querySelector('#genereSel').classList.remove("errorInputs");
        nValids++;
    }
    else {
        document.querySelector('#genereL').classList.add("errorInputs");
        document.querySelector('#genereSel').classList.add("errorInputs");
        errors.push("Genere: Si us plau seleccioni un genere");
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
            errors.push("Telefon: El format del telefon es invalid");
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
        errors.push("Nacionalitat: Si us plau seleccioni una nacionalitat");
    }
    if((nValids == 11 && telOptional == true) || (nValids == 12 && telOptional == false)) {
        errorAlert.classList.add("d-none");
        f_ferPeticioAjax("POST", "http://localhost/projecte/CalendarApp/public/register/save", true, generarLiniaDeParametres({email:email,nom:nom,cognom1:cognom1,cognom2:cognom2,pass:passwd, genere:genere,telefon:telefon,nacionalitat,nacionalitat, dataNaix:dataNaix}),f_enviarALogin, f_notificarError);
    }
    else {
        let errorAlert = document.querySelector('#errorAlert');
        errorAlert.classList.remove("d-none");
        errorAlert.textContent = "";
        let ul = document.createElement('ul');
        for(let i = 0; i < errors.length; i++) {
            let line = document.createElement('li');
            line.appendChild(document.createTextNode(errors[i]));
            ul.appendChild(line);
        }
        errorAlert.appendChild(ul);
    }
}

function f_enviarALogin(data) {
    let resposta = tryParseJSONObject(data.responseText);
    user = tryParseJSONObject(resposta.message);
    if(resposta != false) {
        let message = "";
        if(resposta.emailState == 1) {
            message = encodeURIComponent('Registre Realitzat Correctament. En el correu ' + user.email +' se l\'hi ha enviat un correu de validacio del compte');
        }
        else {
            message = encodeURIComponent('Registre Realitzat Correctament. No se li ha pogut enviar un correu de la validacio del compte en el correu ' + user.email );
        }
        window.location.href = "http://localhost/projecte/CalendarApp/public/login?message="+message;
    }

}
function f_notificarError(data) {
    let resposta = tryParseJSONObject(data.responseText);
    user = tryParseJSONObject(resposta.message);
    if(resposta != false) {
        let errorMessage = document.querySelector('#errorMessage');
        errorMessage.classList.remove('d-none');
        errorMessage.textContent = resposta.message;
        document.querySelector("#emailL").classList.add("errorInputs");
        document.querySelector("#emailInput").classList.add("errorInputs");
        setTimeout(f_removeAlert, 5000);
    }
}

function f_removeAlert() {
    let errorMessage = document.querySelector('#errorMessage');
    errorMessage.classList.add('d-none');
    errorMessage.textContent = "";
}

function f_ferPeticioAjax(method, url, async, data, functToExecuteWhenAllGood, functToExecuteWhenAllBad) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            functToExecuteWhenAllGood(this);
        }
        else if(this.readyState == 4 && this.status == 400) {
            functToExecuteWhenAllBad(this);
        }
    };
    xmlhttp.open(method, url, async);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");   
    xmlhttp.setRequestHeader("X-CSRF-TOKEN", document.querySelector("input[name='_token']").value);     
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
    let objectLen = Object.keys(data).length;
    for(let key in data) {
        txt += key + "="+data[key];
        if(i < objectLen) {
            txt += "&";
        }
        i++;
    }
    return txt;
}