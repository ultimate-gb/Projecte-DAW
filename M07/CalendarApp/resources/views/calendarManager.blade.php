@extends('layouts.baseToReturnBack')

@section('webTitle', "Index")

@section('returnBack', route("index"))

@section('webZone', "Calendari Manager")

@section('bodyId', "calendariManager")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }} top-message" role="alert">
            {{ $message }}
        </div>
    @endif
    <h2>{{ $opType }} Calendari: </h2>
    @if ($op == "del")
        <p>Estas Segur que vols esborar el calendari. Si aixo es cert pressiona el buto de esborrar. Si no es pot esborrar tambe se li notificara</p>
    @endif
    <form class="container mt-3" method="POST" action="{{ route('calendar.'.$op) }}">
        @csrf
        @if ($op != "del")
            <div class="input-group mb-3">
                <label class="input-group-text" for="nomInput">Nom</label>
                <input type="text" class="form-control" id="nomInput" name="nom" placeholder="Introdueixi un Nom per al calendari" value="{{ $calendar->nom }}" aria-label="Nom" aria-describedby="nomInput">
            </div>
        @endif
        @if ($op == "add")
        <input type="hidden" name="user" value="{{ $user }}">
        @endif
        @if ($op == "edit" || $op == "del")
            <input type="hidden" name="id" value="{{ $id }}">
            @method("DELETE")
        @endif
        <div class="d-flex justify-content-end">
            <input type="submit" name="{{ $op }}" value="{{ $opType }}" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection