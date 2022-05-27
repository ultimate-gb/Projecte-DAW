"use strict";

addEventListener('DOMContentLoaded', f_main);

function f_main() {
  var showPasswdCk = document.querySelector("#showPasswd");
  var passwdInput = document.querySelector("#passInput");
  showPasswdCk.addEventListener('click', function () {
    if (showPasswdCk.checked == true) {
      passwdInput.setAttribute('type', 'text');
    } else {
      passwdInput.setAttribute('type', 'password');
    }
  });
}