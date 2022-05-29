addEventListener('DOMContentLoaded', f_main);
function f_main() {
    let inputs = document.querySelectorAll('form input:not([type=checkbox])');
    for(let i = 0; i < inputs.length; i++) {
        inputs[i].addEventListener('focus', f_focusOn);
        inputs[i].addEventListener('blur', f_focusOut);
    }
    let passInputs = document.querySelectorAll('#passInput');
    for(let i = 0; i < passInputs.length; i++) {
        passInputs[i].addEventListener('keydown', f_keyDown);
    }
    document.addEventListener('keypress', f_majusState);
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
let majusActive = false;
function f_majusState(ev) {
    let keyCode = ev.keyCode;
    var charCode = false;
        
    if (ev.which) {
        charCode = ev.which;
    } 
    else if (ev.keyCode) {
        charCode = ev.keyCode;
    }
    let shifton = false;
    if (ev.shiftKey) {
        shifton = ev.shiftKey;
    }
    else if (ev.modifiers) {
        shifton = !!(ev.modifiers & 4);
    }
    console.log(shifton);
    if (charCode >= 97 && charCode <= 122 && shifton) {
        majusActive = true;
        return;
    }
    if (charCode >= 65 && charCode <= 90 && !shifton) {
        majusActive = true;
        return;
    }
    majusActive = false;
    return; 
}
function f_keyDown(ev) {
    let input = ev.target;
    let p = input.nextElementSibling;
    let keyCode = ev.keyCode;
    if(keyCode == 20) {
        if(majusActive) {
            p.classList.remove("d-none");
            majusActive=false;
        }
        else {
            p.classList.add("d-none");
            majusActive=true;
        }
    }
    else {
        if(majusActive) {
            p.classList.remove("d-none");
        }
        else {
            p.classList.add("d-none");
        }
    }
}