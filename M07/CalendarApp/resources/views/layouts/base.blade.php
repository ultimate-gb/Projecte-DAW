<!DOCTYPE html>
<html lang="en" id='projecteCalendarApp'>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="{{ asset('css/style.css') }}">
    <title>Calendar App - @yield('webTitle')</title>
    <script src="https://kit.fontawesome.com/4bb281dcf1.js" crossorigin="anonymous"></script>
    <script src="{{ asset('js/script.js') }}"></script>
</head>
<body id='@yield("bodyId")'>
    <header>
		<nav class="navbar navbar-light bg-newblue">
			<div class="container text-white">
				<a class="navbar-brand text-white h1">Calendar App - @yield('webZone')</a>
				<a href="{{ route('login.logout') }}" ><i class="fas fa-sign-out-alt text-white"></i></a>
			</div>
		</nav>
	</header>
    <main class="container">
        @section('mainContent')
        
        @show
    </main>
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>