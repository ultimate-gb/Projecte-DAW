@extends('layouts.baseToReturnBack')

@section('webTitle', "Ajudant Manager")

@section('returnBack', route("calendar.see", ["id"=>$calendar]))

@section('webZone', "Ajudant Manager")

@section('bodyId', "helperManager")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
    <script src="{{ asset('js/helperScript.js') }}"></script>
@endsection

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }} top-message" role="alert">
            {{ $message }}
        </div>
    @endif
    <h2>{{ $opType }} Ajudant: </h2>
    @if ($op == "del")
        <p>Estas Segur que vols esborar l'Ajudant. Si aixo es cert pressiona el butó d'esborrar. Si no és pot esborrar tambe se li notificara</p>
    @endif
    <form class="container mt-3" id="helpForm" method="POST" action="{{ route('ajudants.'.$op) }}">
        @csrf
        @if ($op != "del")
            <div class="form-floating mb-3 selectDiv">
                <select class="form-select" id="userList" name="user" aria-label="Selecciona un Usuari">
                    <option disabled selected value="-1">Selecciona un Usuari afegir</option>
                    @foreach ($usuaris as $item)
                        <option value="{{ $item->id }}">{{ $item->nom . " " . $item->cognoms }}</option>
                    @endforeach
                    <option value="0">Vull afegir un Ajudant que no esta llistat</option>
                </select>
                <label for="userList">Ajudant</label>
            </div>
            <div class="form-floating mb-3 d-none" id="userZone">
                <input type="email" class="form-control" name="email" id="emailInput" placeholder="">
                <label for="emailInput">Correu Electronic</label>
              </div>
        @endif
        @if ($op == "add")
            <input type="hidden" name="calendari" value="{{ $calendar }}">
        @endif
        @if($op == "del")
            @method("DELETE")
        @endif
        <div class="d-flex justify-content-end">
            <input type="submit" name="{{ $op }}" value="{{ $opType }}" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection