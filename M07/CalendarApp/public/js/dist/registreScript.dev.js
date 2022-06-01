"use strict";

function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

addEventListener('DOMContentLoaded', f_main);

function f_main() {
  var registerButton = document.querySelector("#registerBtn");
  registerButton.addEventListener('click', function (ev) {
    ev.preventDefault();
    validarForm();
  });
}

function validarForm() {
  var email = document.querySelector("#emailInput").value;
  var nom = document.querySelector('#nomInput').value;
  var cognom1 = document.querySelector('#cognom1Input').value;
  var cognom2 = document.querySelector('#cognom2Input').value;
  var passwd = document.querySelector('#passInput').value;
  var dataNaix = document.querySelector('#dataNaixInput').value;
  var genere = document.querySelector('#genereSel').value;
  var telefon = document.querySelector('#telefonInput').value;
  var nacionalitat = document.querySelector('#nacionalitatSel').value;
  var token = document.querySelector('#token');
  var nValids = 0;
  var telOptional = false;
  var errors = new Array();

  if (email.match(new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "g"))) {
    document.querySelector("#emailL").classList.remove("errorInputs");
    document.querySelector("#emailInput").classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#emailL").classList.add("errorInputs");
    document.querySelector("#emailInput").classList.add("errorInputs");
    errors.push("Email: Format Email Invalid");
  }

  if (nom.length <= 40) {
    document.querySelector("#nomL").classList.remove("errorInputs");
    document.querySelector('#nomInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#nomL").classList.add("errorInputs");
    document.querySelector('#nomInput').classList.add("errorInputs");
    errors.push("Nom: Longitud del Nom massa llarga");
  }

  if (nom.match(new RegExp('^[A-Z][a-z]+\\s?[A-Z]?[a-z]*$'))) {
    document.querySelector("#nomL").classList.remove("errorInputs");
    document.querySelector('#nomInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#nomL").classList.add("errorInputs");
    document.querySelector('#nomInput').classList.add("errorInputs");
    errors.push("Nom: Format Nom Invalid. Hauria de començar amb lletra majuscula seguit de lletres minuscules");
  }

  if (cognom1.match(new RegExp('^[A-Z][a-z]{1,100}$'))) {
    document.querySelector("#cognom1L").classList.remove("errorInputs");
    document.querySelector('#cognom1Input').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#cognom1L").classList.add("errorInputs");
    document.querySelector('#cognom1Input').classList.add("errorInputs");
    errors.push("Primer Cognom: Format Primer Cognom Invalid. Hauria de començar amb lletra majuscula seguit de lletres minuscules");
  }

  if (cognom2.length == 0 || cognom2.match(new RegExp('^([A-Z][a-z]{0,100})?$'))) {
    document.querySelector("#cognom2L").classList.remove("errorInputs");
    document.querySelector('#cognom2Input').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#cognom2L").classList.add("errorInputs");
    document.querySelector('#cognom2Input').classList.add("errorInputs");
    errors.push("Segon Cognom: Format Segon Cognom Invalid. Hauria de començar amb lletra majuscula seguit de lletres minuscules");
  }

  if (cognom1.length + cognom2.length <= 100) {
    if (cognom1.length > 0 && cognom1.length <= 100) {
      document.querySelector("#cognom1L").classList.remove("errorInputs");
      document.querySelector('#cognom1Input').classList.remove("errorInputs");
    }

    if (cognom2.length == 0 || cognom2.length > 0 && cognom2.length <= 100) {
      document.querySelector("#cognom2L").classList.remove("errorInputs");
      document.querySelector('#cognom2Input').classList.remove("errorInputs");
    }

    nValids++;
  } else {
    document.querySelector("#cognom1L").classList.add("errorInputs");
    document.querySelector('#cognom1Input').classList.add("errorInputs");
    document.querySelector("#cognom2L").classList.add("errorInputs");
    document.querySelector('#cognom2Input').classList.add("errorInputs");
    errors.push("Cognoms: La longitud del dos cognoms afegint un espai al mig no pot superar els 100 caracters de longitud.");
  }

  if (passwd.match("^\\w+")) {
    document.querySelector("#passwdL").classList.remove("errorInputs");
    document.querySelector('#passInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#passwdL").classList.add("errorInputs");
    document.querySelector('#passInput').classList.add("errorInputs");
    errors.push("Password: Format Password Invalid");
  }

  if (dataNaix.length > 0) {
    document.querySelector("#dataNaixL").classList.remove("errorInputs");
    document.querySelector('#dataNaixInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#dataNaixL").classList.add("errorInputs");
    document.querySelector('#dataNaixInput').classList.add("errorInputs");
    errors.push("Data Naixement: Camp obligatori");
  }

  var data = new Date(dataNaix);
  var today = new Date();

  if (data.getUTCFullYear() <= today.getUTCFullYear() && today.getUTCFullYear() - data.getUTCFullYear() >= 18) {
    document.querySelector("#dataNaixL").classList.remove("errorInputs");
    document.querySelector('#dataNaixInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#dataNaixL").classList.add("errorInputs");
    document.querySelector('#dataNaixInput').classList.add("errorInputs");
    errors.push("Data Naixement: Per poder registrar-se ha de ser major de 18 anys o cumplir els 18 aquest any");
  }

  if (genere != -1) {
    document.querySelector('#genereL').classList.remove("errorInputs");
    document.querySelector('#genereSel').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector('#genereL').classList.add("errorInputs");
    document.querySelector('#genereSel').classList.add("errorInputs");
    errors.push("Genere: Si us plau seleccioni un genere");
  }

  if (telefon.length == 0) {
    document.querySelector('#telefonL').classList.remove("errorInputs");
    document.querySelector('#telefonInput').classList.remove("errorInputs");
    telOptional = true;
  }

  if (telefon.length > 0 && telefon.match(new RegExp('^\\+?\\d{1,20}$'))) {
    document.querySelector('#telefonL').classList.remove("errorInputs");
    document.querySelector('#telefonInput').classList.remove("errorInputs");
    nValids++;
  } else {
    if (telOptional == false) {
      document.querySelector('#telefonL').classList.add("errorInputs");
      document.querySelector('#telefonInput').classList.add("errorInputs");
      errors.push("Telefon: El format del telefon es invalid");
    }
  }

  if (nacionalitat != -1) {
    document.querySelector('#nacL').classList.remove("errorInputs");
    document.querySelector('#nacionalitatSel').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector('#nacL').classList.add("errorInputs");
    document.querySelector('#nacionalitatSel').classList.add("errorInputs");
    errors.push("Nacionalitat: Si us plau seleccioni una nacionalitat");
  }

  if (nValids == 11 && telOptional == true || nValids == 12 && telOptional == false) {
    errorAlert.classList.add("d-none");
    var _data = "";

    if (token != null) {
      var _generarLiniaDeParame;

      _data = generarLiniaDeParametres((_generarLiniaDeParame = {
        email: email,
        nom: nom,
        cognom1: cognom1,
        cognom2: cognom2,
        pass: passwd,
        genere: genere,
        telefon: telefon,
        nacionalitat: nacionalitat
      }, _defineProperty(_generarLiniaDeParame, "nacionalitat", nacionalitat), _defineProperty(_generarLiniaDeParame, "dataNaix", dataNaix), _defineProperty(_generarLiniaDeParame, "token", token), _generarLiniaDeParame));
    } else {
      var _generarLiniaDeParame2;

      _data = generarLiniaDeParametres((_generarLiniaDeParame2 = {
        email: email,
        nom: nom,
        cognom1: cognom1,
        cognom2: cognom2,
        pass: passwd,
        genere: genere,
        telefon: telefon,
        nacionalitat: nacionalitat
      }, _defineProperty(_generarLiniaDeParame2, "nacionalitat", nacionalitat), _defineProperty(_generarLiniaDeParame2, "dataNaix", dataNaix), _defineProperty(_generarLiniaDeParame2, "token", ""), _generarLiniaDeParame2));
    }

    f_ferPeticioAjax("POST", "http://localhost:8081/projecte/CalendarApp/public/register/save", true, _data, f_enviarALogin, f_notificarError);
  } else {
    var _errorAlert = document.querySelector('#errorAlert');

    _errorAlert.classList.remove("d-none");

    _errorAlert.textContent = "";
    var ul = document.createElement('ul');

    for (var i = 0; i < errors.length; i++) {
      var line = document.createElement('li');
      line.appendChild(document.createTextNode(errors[i]));
      ul.appendChild(line);
    }

    _errorAlert.appendChild(ul);
  }
}

function f_enviarALogin(data) {
  var resposta = tryParseJSONObject(data.responseText);
  user = tryParseJSONObject(resposta.message);

  if (resposta != false) {
    var message = "";

    if (resposta.emailState == 1) {
      message = encodeURIComponent('Registre Realitzat Correctament. En el correu ' + user.email + ' se l\'hi ha enviat un correu de validacio del compte');
    } else {
      message = encodeURIComponent('Registre Realitzat Correctament. No se li ha pogut enviar un correu de la validacio del compte en el correu ' + user.email);
    }

    window.location.href = "http://localhost:8081/projecte/CalendarApp/public/login?message=" + message;
  }
}

function f_notificarError(data) {
  var resposta = tryParseJSONObject(data.responseText);
  user = tryParseJSONObject(resposta.message);

  if (resposta != false) {
    var errorMessage = document.querySelector('#errorMessage');
    errorMessage.classList.remove('d-none');
    errorMessage.textContent = resposta.message;
    setTimeout(f_removeAlert, 5000);
  }
}

function f_removeAlert() {
  var errorMessage = document.querySelector('#errorMessage');
  errorMessage.classList.add('d-none');
  errorMessage.textContent = "";
}

function f_ferPeticioAjax(method, url, async, data, functToExecuteWhenAllGood, functToExecuteWhenAllBad) {
  var xmlhttp = new XMLHttpRequest();

  xmlhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      functToExecuteWhenAllGood(this);
    } else if (this.readyState == 4 && this.status == 400) {
      functToExecuteWhenAllBad(this);
    }
  };

  xmlhttp.open(method, url, async);
  xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xmlhttp.setRequestHeader("X-CSRF-TOKEN", document.querySelector("input[name='_token']").value);
  xmlhttp.send(data);
  return xmlhttp;
} // Comprova si el json te un format valid i tambe comprova que si no peta retorni un objecte


function tryParseJSONObject(jsonString) {
  try {
    var o = JSON.parse(jsonString);

    if (o && _typeof(o) === "object") {
      return o;
    }
  } catch (e) {}

  return false;
}

;

function generarLiniaDeParametres(data) {
  var txt = "";
  var i = 0;
  var objectLen = Object.keys(data).length;

  for (var key in data) {
    txt += key + "=" + data[key];

    if (i < objectLen) {
      txt += "&";
    }

    i++;
  }

  return txt;
}