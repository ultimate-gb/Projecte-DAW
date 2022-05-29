document.addEventListener('DOMContentLoaded', f_main);

function f_main() {
    let main = document.querySelector('#zonaPrincipal');
    let titol = document.createElement('h2');
    titol.appendChild(document.createTextNode("Funcionament Aplicació"));
    let p = document.createElement('p');
    p.appendChild(document.createTextNode("Aquesta aplicacio et permet crear un calendari, amb el qual pots assignar-li ajudants. Aquest ajudants podran crear, editar i eliminar les activitats que ells mateix creein mentres no estiguin publicades. El propietari del calendari decideix quines activitats publica a més de poder crear, editar i eliminar totes les activitats del calendari."))
    let nouP = document.createElement('p');
    nouP.appendChild(document.createTextNode("Els tipus de activitats que tan el propietari com els helpers poden crear depenen exclusivament del propietari. A més el propietari del calendari el podra esborrar sempre i quan no tingui ni ajudants ni activitats."));
    let nouP2 = document.createElement('p');
    nouP2.appendChild(document.createTextNode("Per ultim tots els usuaris podran exportar les activitats del calendaris tan si son target, com propietari o ajudant en format csv."));
    main.appendChild(titol);
    main.appendChild(p);
    main.appendChild(nouP);
    main.appendChild(nouP2);
}