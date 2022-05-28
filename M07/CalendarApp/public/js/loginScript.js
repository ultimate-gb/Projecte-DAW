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

    // Codi Per mostrar alertes en login
    let alert = document.querySelector('#topAlert');
    let queryString = window.location.search;
    let urlParams = new URLSearchParams(queryString);
    let message =  urlParams.get('message');
    if(message != null && message.length > 0) {
        alert.classList.remove("d-none");
        alert.classList.add('alert-success');
        alert.textContent = message;
    }
    else {
        console.log(alert.textContent.length);
        if(alert.textContent.length > 14) {
            alert.classList.remove("d-none");
            alert.classList.add('alert-danger');
        }
    }
}