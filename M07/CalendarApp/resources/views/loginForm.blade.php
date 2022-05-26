@extends('layouts.default')

@section('webTitle', "Login")

@section('bodyId', "loginForm")

@section('mainContent')
    @if(strlen($message) > 0) 
        <div class="alert alert-danger topMessage" role="alert">
            {{ $message }}
        </div>
    @endif
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
        </div>
        <div class="input-group mb-3">
            <input class="form-check-input" type="checkbox" id="showPasswd">
            <label class="form-check-label" for="showPasswd">
              Mostrar Contrassenya
            </label>
        </div>
        <div class="col-12 d-flex justify-content-center">
            <button type="submit" name='loginBtn' class="btn btnLogin btn-primary bg-newblue">Iniciar Sessio</button>
        </div>
    </form>
@endsection