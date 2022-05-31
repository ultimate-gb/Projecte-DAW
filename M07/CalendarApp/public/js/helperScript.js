document.addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let sel = document.querySelector('#userList');
    sel.addEventListener('change', f_changeSel);
}

function f_changeSel(ev) {
    let sel = ev.target;
    let userZone = document.querySelector('#userZone');
    if(sel.value == 0) {
        userZone.classList.remove('d-none');
    }
    else {
        userZone.classList.add('d-none');
    }
}