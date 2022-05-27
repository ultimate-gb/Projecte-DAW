@extends('layouts.default')

@section('webTitle', "Registre")

@section('bodyId', "registerForm")

@php
    $labelClassCols = "col-4";
    $inputClassCols = "col-8";
@endphp
@section('scriptHeaderZone')
    <script src="{{ asset('js/registreScript.js') }}"></script>
@endsection

@section('mainContent')
    @if(strlen($message) > 0) 
        <div class="alert alert-danger topMessage" role="alert">
            {{ $message }}
        </div>
    @endif
    <header>
        <h1>Registre</h1>
    </header>
    <form action="{{route('register.save')}}" method="post">
        @csrf
        <div class="input-group mb-4">
            <label id="emailL" class="input-group-text  {{ $labelClassCols }}" for="emailInput">Correu Electronic</label>
            <input type="text" name='email' class="form-control {{ $inputClassCols }}" id="emailInput" placeholder="Correu Electronic" aria-label="Mail" aria-describedby="mail">
        </div>
        <div class="input-group mb-3">
            <label id="nomL" class="input-group-text   {{ $labelClassCols }}" for="nomInput">Nom</label>
            <input type="text" name='nom' class="form-control {{ $inputClassCols }}" id="nomInput" placeholder="Nom" aria-label="nom" aria-describedby="nom">
        </div>
        <div class="input-group mb-3">
            <label id="cognom1L" class="input-group-text   {{ $labelClassCols }}" for="cognom1Input">Primer Cognom</label>
            <input type="text" name='cognom1' class="form-control {{ $inputClassCols }}" id="cognom1Input" placeholder="Primer Cognom" aria-label="Cognom1" aria-describedby="cognom1">
        </div>
        <div class="input-group mb-3">
            <label id="cognom2L" class="input-group-text   {{ $labelClassCols }}" for="cognom2Input">Segon Cognom</label>
            <input type="text" name='cognom2' class="form-control {{ $inputClassCols }}" id="cognom2Input" placeholder="Segon Cognom" aria-label="Cognom2" aria-describedby="cognom2">
        </div>
        <div class="input-group mb-3">
            <label id="passwdL" class="input-group-text   {{ $labelClassCols }}" for="passInput">Password</label>
            <input type="password" name='pass' class="form-control {{ $inputClassCols }}" id="passInput" placeholder="Contrassenya" aria-label="Password" aria-describedby="password">
        </div>
        <div class="input-group mb-3">
            <label id="dataNaixL" class="input-group-text   {{ $labelClassCols }}" for="dataNaixInput">Data Naixement</label>
            <input type="date" name='dataNaix' class="form-control {{ $inputClassCols }}" id="dataNaixInput" placeholder="" aria-label="Data Naixement" aria-describedby="data_naixement">
        </div>
        <div class="input-group mb-3">
            <label id="telefonL" class="input-group-text  {{ $labelClassCols }}" for="telefonInput">Telefon</label>
            <input type="tel" name='telefon' class="form-control {{ $inputClassCols }}" id="telefonInput" placeholder="Telefon" aria-label="Telefon" aria-describedby="telefon">
        </div>
        <div class="input-group mb-3">
            <label id="genereL" class="input-group-text {{ $labelClassCols }}" for="genereSel">Genere</label>
            <select class="form-select {{ $inputClassCols }}" id="genereSel" name="genere">
              <option selected disabled value="-1">Selecciona Genere</option>
              <option value="H">Home</option>
              <option value="D">Dona</option>
              <option value="I">No Respondre</option>
            </select>
        </div>
        <div class="input-group mb-3">
            <label id="nacL" class="input-group-text {{ $labelClassCols }}" for="nacionalitatSel">Nacionalitat</label>
            <select class="form-select {{ $inputClassCols }}" id="nacionalitatSel" name="nacionalitat">
              <option selected disabled value="-1">Selecciona Nacionalitat.</option>
              @foreach ($nacionalities as $nacionality)
                  <option value="{{ $nacionality->codi }}">{{ $nacionality->nom }}</option>
              @endforeach
            </select>
        </div>
        <div class="col-12 d-flex gap-2 buttonGroup">
            <button type="submit" name='registerBtn' id="registerBtn" class="btn btn-primary bg-newblue w-100">Registrar-se</button>
        </div>
    </form>
@endsection