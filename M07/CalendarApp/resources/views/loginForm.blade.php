@extends('layouts.default')

@section('webTitle', "Login")

@section('bodyId', "loginForm")

@section('scriptHeaderZone')
    <script src="{{ asset('js/loginScript.js') }}"></script>
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@section('mainContent')
    <div class="alert topMessage d-none alert-{{ $tipus }}" role="alert" id="topAlert">
        {{ $message }}
    </div>
    <header>
        <h1>Login</h1>
    </header>
    <form action="{{route('login.check')}}" method="post">
        @csrf
        <div class="input-group mb-3">
            <label class="input-group-text d-flex justify-content-center" for="emailInput"><i class="fas fa-at"></i></label>
            <input type="text" name='email' class="form-control" id="emailInput" placeholder="Correu Electronic" aria-label="Mail" aria-describedby="mail">
        </div>
        <div class="input-group mb-3">
            <label class="input-group-text d-flex justify-content-center" for="passInput"><i class="fas fa-key"></i></label>
            <input type="password" name='pass' class="form-control" id="passInput" placeholder="Contrassenya" aria-label="Password" aria-describedby="password">
            <p class="d-none col-12 align-self-center" id="majusInfo">Majuscules Actives</p>
        </div>
        <div class="input-group mb-3 checkboxGroup">
            <input class="form-check-input" type="checkbox" id="showPasswd">
            <label class="form-check-label" for="showPasswd">
              Mostrar Contrassenya
            </label>
        </div>
        <div class="col-12 d-flex gap-2 buttonGroup">
            <button type="submit" name='loginBtn' class="btn btnLogin btn-primary bg-newblue w-100">Iniciar Sessio</button>
            <a role='button' href='{{ route("register") }}' class='btn btn-primary btnLogin bg-newblue w-100'>Registrar-se</a>
        </div>
    </form>
@endsection