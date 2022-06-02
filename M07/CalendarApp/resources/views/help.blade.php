<!DOCTYPE html>
<html lang="en" id='projecteCalendarApp'>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="{{ asset('css/style.css') }}">
    <title>Calendar App - Ajuda</title>
    <script src="https://kit.fontawesome.com/4bb281dcf1.js" crossorigin="anonymous"></script>
    <script src="{{ asset('js/helpScript.js') }}"></script>
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
                setTimeout(() => {
                    document.querySelector('body').removeChild(fadeTarget);
                }, 700);
            }
        </script>
</head>
<body id='help' class="app">
    <header>
		<nav class="navbar navbar-light bg-newblue">
			<div class="container text-white">
				<a class="navbar-brand text-white h1">Calendar App - Ajuda</a>
				<a href="{{ route('login.logout') }}" ><i class="fas fa-sign-out-alt text-white"></i></a>
			</div>
		</nav>
	</header>
    <main class="container" id="zonaPrincipal">
    </main>
    <footer class="bg-newblue">
        <div class="container">
            <p>© Tots els drets reservats</p>
            <p>Gerard Balsells Franquesa</p>
        </div>
    </footer>
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>