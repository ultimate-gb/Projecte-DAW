addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let inputs = document.querySelectorAll('form input');
    for(let i = 0; i < inputs.length; i++) {
        inputs[i].addEventListener('focus', f_focusOn);
        inputs[i].addEventListener('blur', f_focusOut);
    }
}

function f_focusOn(ev) {
    let input = ev.target;
    let label = input.previousElementSibling;
    input.classList.add("onFocus");
    label.classList.add("onFocus");
}

function f_focusOut(ev) {
    let input = ev.target;
    let label = input.previousElementSibling;
    input.classList.remove("onFocus");
    label.classList.remove("onFocus");
}