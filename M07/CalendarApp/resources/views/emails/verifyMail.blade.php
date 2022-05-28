<!DOCTYPE html>
<html>
<head>
 <title>Correu De Validacio De Compte</title>
</head>
<body>
 <p>Salutacions {{ $nom }},</p>
 <p>Li donem la benvinguda a Calendar App. Per completar el registre voste ha de clicar al seguent enllaç: 
    <a href="http://localhost/projecte/CalendarApp/public/registre/completarUsuari/{{ $token }}">http://localhost/projecte/CalendarApp/public/registre/completarUsuari/{{ $token }}</a>
</p>
 
 
</body>
</html> 