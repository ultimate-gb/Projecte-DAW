addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let showPasswdCk = document.querySelector("#showPasswd");
    let passwdInput = document.querySelector("#passInput");
    showPasswdCk.addEventListener('click', function() {
        if(showPasswdCk.checked == true) {
            passwdInput.setAttribute('type', 'text');
        }
        else {
            passwdInput.setAttribute('type', 'password');
        }
    });
}