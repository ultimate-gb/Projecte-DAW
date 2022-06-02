<!DOCTYPE html>
<html lang="en" id='projecteCalendarApp'>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/4bb281dcf1.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="{{ asset('css/style.css') }}">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Baloo+2:wght@400;500;600;700;800&display=swap" rel="stylesheet"> 
    <!-- Esta aqui aquest script perque es el primer que s'ha de mostrar -->
    <script>
        window.addEventListener('load', f_main);

        function f_main() {
            let loader = document.querySelector("#loader");
            fadeOutEffect();
        }

        function fadeOutEffect() {
            let fadeTarget = document.getElementById("loader");
            fadeTarget.style.opacity -= 0.5;
            fadeTarget.style.transition= "opacity 0.7s linear";
        }
    </script>
    @section('scriptHeaderZone')
    @show
    <title>Calendar App - @yield('webTitle')</title>
</head>
<body id='@yield("bodyId")'>
    <main class="container myContanier">
        @section('mainContent')
        
        @show
    </main>
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>