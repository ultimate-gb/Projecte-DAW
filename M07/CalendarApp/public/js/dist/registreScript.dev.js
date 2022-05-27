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
  var nValids = 0;
  var telOptional = false;

  if (email.match(new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "g"))) {
    document.querySelector("#emailL").classList.remove("errorInputs");
    document.querySelector("#emailInput").classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#emailL").classList.add("errorInputs");
    document.querySelector("#emailInput").classList.add("errorInputs");
  }

  if (nom.length <= 40) {
    document.querySelector("#nomL").classList.remove("errorInputs");
    document.querySelector('#nomInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#nomL").classList.add("errorInputs");
    document.querySelector('#nomInput').classList.add("errorInputs");
  }

  if (nom.match(new RegExp('^[A-Z][a-z]+\\s?[A-Z]?[a-z]*$'))) {
    document.querySelector("#nomL").classList.remove("errorInputs");
    document.querySelector('#nomInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#nomL").classList.add("errorInputs");
    document.querySelector('#nomInput').classList.add("errorInputs");
  }

  if (cognom1.match(new RegExp('^[A-Z][a-z]{1,100}$'))) {
    document.querySelector("#cognom1L").classList.remove("errorInputs");
    document.querySelector('#cognom1Input').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#cognom1L").classList.add("errorInputs");
    document.querySelector('#cognom1Input').classList.add("errorInputs");
  }

  if (cognom2.match(new RegExp('^[A-Z][a-z]{0,100}$'))) {
    document.querySelector("#cognom2L").classList.remove("errorInputs");
    document.querySelector('#cognom2Input').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#cognom2L").classList.add("errorInputs");
    document.querySelector('#cognom2Input').classList.add("errorInputs");
  }

  if (cognom1.length + cognom2.length <= 100) {
    document.querySelector("#passwdL").classList.remove("errorInputs");
    document.querySelector('#passInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#passwdL").classList.add("errorInputs");
    document.querySelector('#passInput').classList.add("errorInputs");
  }

  if (passwd.match("^\\w+")) {
    document.querySelector("#passwdL").classList.remove("errorInputs");
    document.querySelector('#passInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#passwdL").classList.add("errorInputs");
    document.querySelector('#passInput').classList.add("errorInputs");
  }

  if (dataNaix.length > 0) {
    document.querySelector("#dataNaixL").classList.remove("errorInputs");
    document.querySelector('#dataNaixInput').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector("#dataNaixL").classList.add("errorInputs");
    document.querySelector('#dataNaixInput').classList.add("errorInputs");
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
  }

  if (genere != -1) {
    document.querySelector('#genereL').classList.remove("errorInputs");
    document.querySelector('#genereSel').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector('#genereL').classList.add("errorInputs");
    document.querySelector('#genereSel').classList.add("errorInputs");
  }

  if (telefon.length == 0) {
    document.querySelector('#telefonL').classList.remove("errorInputs");
    document.querySelector('#telefonInput').classList.remove("errorInputs");
    telOptional = true;
  }

  if (telefon.length > 0 && telefon.match(new RegExp('^[1-9][0-9]{8}$'))) {
    document.querySelector('#telefonL').classList.remove("errorInputs");
    document.querySelector('#telefonInput').classList.remove("errorInputs");
    nValids++;
  } else {
    if (telOptional == false) {
      document.querySelector('#telefonL').classList.add("errorInputs");
      document.querySelector('#telefonInput').classList.add("errorInputs");
    }
  }

  if (nacionalitat != -1) {
    document.querySelector('#nacL').classList.remove("errorInputs");
    document.querySelector('#nacionalitatSel').classList.remove("errorInputs");
    nValids++;
  } else {
    document.querySelector('#nacL').classList.add("errorInputs");
    document.querySelector('#nacionalitatSel').classList.add("errorInputs");
  }

  if (nValids == 11 && telOptional == true || nValids == 12 && telOptional == false) {
    var _generarLiniaDeParame;

    f_ferPeticioAjax("POST", "http://localhost:8081/projecte/CalendarApp/public/register/save", true, generarLiniaDeParametres((_generarLiniaDeParame = {
      email: email,
      nom: nom,
      cognom1: cognom1,
      cognom2: cognom2,
      pass: passwd,
      genere: genere,
      telefon: telefon,
      nacionalitat: nacionalitat
    }, _defineProperty(_generarLiniaDeParame, "nacionalitat", nacionalitat), _defineProperty(_generarLiniaDeParame, "dataNaix", dataNaix), _generarLiniaDeParame)), f_validar);
  }
}

function f_validar(data) {
  var resposta = tryParseJSONObject(data.responseText);

  if (resposta.status = 200) {
    // Enviar al login i notificar
    console.log(resposta);
  } else {// Mostrar alerta que no s'ha pogut registra
  }
}

function f_ferPeticioAjax(method, url, async, data, functToExecute) {
  var xmlhttp = new XMLHttpRequest();

  xmlhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      functToExecute(this);
    }
  };

  xmlhttp.open(method, url, async);
  xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
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

  for (var key in data) {
    txt += key + "=" + data[key];

    if (i < data.length) {
      txt += "&";
    }

    i++;
  }

  return txt;
}