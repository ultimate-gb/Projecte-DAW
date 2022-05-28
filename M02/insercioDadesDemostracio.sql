/* Inserint Usuaris */
INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat ,role, token, validat)  VALUES('gbalsells@milaifontanals.org', 'Gerard', 'Balsells Franquesa', '4b0523991642e703770b92a323787731', date '2002-09-21','H',608975968,(SELECT codi FROM nacionalitat WHERE nom like 'Spanish'), 1, '0d254077542bfc80b43551e9d0a50ada', true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('gerard.proves.01@gmail.com', 'Manel', 'Alfons Enric', '37f14363d3982af4ac1c3a3978d3468d', date '2002-05-19','H',608975967,(SELECT codi FROM nacionalitat WHERE nom like 'Spanish'), "2567347461a61c472885cca7297df32a", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('akokima@gbalsells.home', 'Amaya', 'Kojima', '84caba0cfbd06149477b4e0a7318f4a6', date '2002-07-09','D',608975950,(SELECT codi FROM nacionalitat WHERE nom like 'Japanese'), "c03ea069185c9931e2531b9161dbc9e5", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('skimura@gbalsells.home', 'Sora', 'Kimura', '7225547a8a7a715b84c659110db3300a', date '2002-06-15','D',608975450,(SELECT codi FROM nacionalitat WHERE nom like 'Japanese'), "4e2c0fcc8af1d7984df986b66fa650d6", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('djin@gbalsells.home', 'Dak-Ho', 'Jin', '4a852f24b72855596cb908e882bd3a8e', date '2002-02-02','H',608972950,(SELECT codi FROM nacionalitat WHERE nom like 'South Korean'), "61fd5b819c9ed54d996cdc503d30cfbf", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('hpung@gbalsells.home', 'Hei', 'Pung', '280bd0c1df1fe8d881a68695ae81332c', date '2002-01-06','H',608975650,(SELECT codi FROM nacionalitat WHERE nom like 'South Korean'), "3ad9543a42cc827b95a31cb733ad58cc", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('canurak@gbalsells.home', 'Chanthira', 'Anurak', '2b62d2e2f168db066599e7bb521ea68b', date '2002-01-20','D',608975940,(SELECT codi FROM nacionalitat WHERE nom like 'Thai'), "cd21023cdabd32647a6e723cca67ef8b", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('cnoknoi@gbalsells.home', 'Chaem Choi', 'Noknoi', '9a267917ee7ed33ac6e4cc2b5c235725', date '2002-03-05','D',608974940,(SELECT codi FROM nacionalitat WHERE nom like 'Thai'), "809e56fb3e955db994d6603ff3ee150a", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('bliu@gbalsells.home', 'Bo', 'Liu', 'dcc976e5f3f53c0bf342919820b45b56', date '2002-01-05','H',608975960,(SELECT codi FROM nacionalitat WHERE nom like 'Chinese'), "5213bacf02af8e1dab5e632ad5c84c22", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, token, validat)  VALUES('dsun@gbalsells.home', 'Donghai', 'Sun', '9e7c0b97808e0825b82e1950e6ffd9db', date '2002-08-06','H',608675560,(SELECT codi FROM nacionalitat WHERE nom like 'Chinese'), "2f134877a8d78908dd75d6f01d7bc165", true);

INSERT INTO users(email,nom,cognoms,password,data_naix,genere,telefon, nacionalitat, bloquejat, role, token, validat)  VALUES('dfuentes1@milaifontanals.org', 'Prova', 'Hernandez Garcia', '189bbbb00c5f1fb7fba9ad9285f193d1', date '2000-04-10','H',608975967,(SELECT codi FROM nacionalitat WHERE nom like 'Spanish'), true, 1, "63903ede42e8129d66e19dc89a22798e", true);

/* Inseritn Calendaris i assignant-los a algun usuari */
INSERT INTO calendari(nom,data_creacio,user) VALUES("DAW 2n", date '2021-09-14',1);

INSERT INTO calendari(nom,data_creacio,user) VALUES("DAW 1r", date '2021-09-14',2);

/* Inserint tipus de activitat a dos usuaris */
INSERT INTO tipus_activitat(codi,nom, user) VALUES (1,'Excursio' , 1);

INSERT INTO tipus_activitat(codi, nom,user) VALUES (2,'Xerrada', 1);

INSERT INTO tipus_activitat(codi,nom,user) VALUES(3,'Activitats', 1);

INSERT INTO tipus_activitat(codi,nom,user) VALUES(4,'Sortida', 1);

INSERT INTO tipus_activitat(codi,nom,user) VALUES(5,'Tallers', 1);

INSERT INTO tipus_activitat(codi,nom, user) VALUES (6,'Excursio' , 2);

INSERT INTO tipus_activitat(codi, nom,user) VALUES (7,'Xerrada', 2);

INSERT INTO tipus_activitat(codi,nom,user) VALUES(8,'Activitats', 2);

INSERT INTO tipus_activitat(codi,nom,user) VALUES(9,'Sortida', 2);

INSERT INTO tipus_activitat(codi,nom,user) VALUES(10,'Tallers', 2);

/* Inserint activitats */
INSERT INTO activitat(calendari,nom, data_inici, data_fi, descripcio, user, tipus) VALUES(1,'Xerrada Trilogi','2022-06-30 15:00:00' , '2022-06-30 17:00:00', "Una xerrada de presentacio de l'empresa",1, 2);

INSERT INTO activitat(calendari,nom, data_inici, data_fi, descripcio, user, tipus) VALUES(1,'Sortida Exterior','2022-07-01 15:00:00', '2022-07-01 15:00:00', "Sortida a el autoforum de tarragona",2, 9);
/* Inserint target del calendari */
INSERT INTO calendari_target(email, calendar) VALUES ('gerard.proves.01@gmail.com', 1);

INSERT INTO calendari_target(email, calendar) VALUES ('gerard.proves.02@gmail.com', 1);

INSERT INTO calendari_target(email, calendar) VALUES ('dfuentes1@milaifontanals.org', 1);

INSERT INTO calendari_target(email, calendar) VALUES ('gbalsells@milaifontanals.org', 1);

/* Inserint Ajudants */
INSERT INTO ajuda(user, calendari) VALUES(1,2);
INSERT INTO ajuda(user,calendari) VALUES(3,2);
INSERT INTO ajuda(user,calendari) VALUES(4,1);