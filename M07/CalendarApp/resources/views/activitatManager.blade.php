@extends('layouts.baseToReturnBack')

@section('webTitle', "Activitat")

@section('returnBack', route("calendar.see", ["id"=>$calendar]))

@section('webZone', "Activitat Manager")

@section('bodyId', "activitatManager")

@section('scriptHeaderZone')
    <script src="{{ asset('js/inputsScript.js') }}"></script>
@endsection

@php
    $labelClassCols = "col-2";
    $inputClassCols = "col-10";
    $nom = $activitat->nom;
    $dataInici = $actDataInici;
    $dataFi = $actDataFi;
    $descripcio = $activitat->descripcio;
    $tipus = $activitat->tipus;
    if(strlen(old('nom')) > 0)  {
        $nom = old('nom');
    }
    if(strlen(old('dataInici')) > 0)  {
        $dataInici = old('dataInici');
    }
    if(strlen(old('dataFi')) > 0)  {
        $dataFi = old('dataFi');
    }
    if(strlen(old('descripcio')) > 0)  {
        $descripcio = old('descripcio');
    }
    if(strlen(old('tipus')) > 0)  {
        $tipus = old('tipus');
    }
@endphp

@section('mainContent')
    @if (strlen($message))
        <div class="alert alert-{{ $tipus }} top-message" role="alert">
            {{ $message }}
        </div>
    @endif
    @if ($errors->any())
    <div class="alert alert-danger">
        <ul>
            @foreach ($errors->all() as $error)
                <li>{{ $error }}</li>
            @endforeach
        </ul>
    </div>
@endif
    <h2>{{ $opType }} Activitat: </h2>
    @if ($op == "del")
        <p>Estas Segur que vols esborar el activitat. Si aixo es cert pressiona el buto de esborrar. Si no es pot esborrar tambe se li notificara</p>
    @endif
    <form class="container mt-3" method="POST" action="{{ route('activitat.'.$op) }}">
        @csrf
        @if ($op != "del")
            <div class="input-group mb-3">
                <label class="input-group-text {{ $labelClassCols }}" for="nomInput">Nom</label>
                <input type="text" class="form-control {{ $inputClassCols }}" id="nomInput" name="nom" placeholder="Introdueixi un Nom per l'activitat" value="{{ $nom }}" aria-label="Nom" aria-describedby="nomInput">
            </div>
            <div class="input-group mb-3">
                <label class="input-group-text {{ $labelClassCols }}" for="dtIniciInput">Data Inici</label>
                <input type="datetime-local" class="form-control {{ $inputClassCols }}" id="dtIniciInput" name="dataInici" placeholder="" value="{{ $dataInici }}" aria-label="DataInici" aria-describedby="dtIniciInputInput">
            </div>
            <div class="input-group mb-3">
                <label class="input-group-text {{ $labelClassCols }}" for="dtIniciInput">Data Fi</label>
                <input type="datetime-local" class="form-control {{ $inputClassCols }}" id="dtFiInput" name="dataFi" placeholder="" value="{{ $dataFi }}" aria-label="DataFi" aria-describedby="dtFiInput">
            </div>
            <div class="input-group mb-3">
                <label class="input-group-text {{ $labelClassCols }}" for="descInput">Descripcio</label>
                <textarea class="form-control {{ $inputClassCols }}" name="descripcio" aria-label="Descripcio">{{ $descripcio }}</textarea>
            </div>
            <div class="input-group mb-3">
                <label class="input-group-text {{ $labelClassCols }}" for="tipusInput">Tipus</label>
                <select class="form-select {{ $inputClassCols }}" id="tipusInput" name="tipus">
                  <option selected value="-1" disabled>Selecciona Un Tipus</option>
                  @foreach ($tipusActivitats as $tp)
                    @php
                        $selected = "";
                        if($tp->codi == $tipus) {
                            $selected = "selected";
                        }
                    @endphp
                      <option value="{{ $tp->codi }}" {{ $selected }}>{{ $tp->nom }}</option>
                  @endforeach
                </select>
            </div>
        @endif
        <input type="hidden" name="calendar" value="{{ $calendar }}">
        @if ($op == "add")
        <input type="hidden" name="user" value="{{ $user }}">
        @endif
        @if ($op == "edit" || $op == "del")
            <input type="hidden" name="id" value="{{ $id }}">
        @endif
        @if($op == "del")
            @method("DELETE")
        @endif
        <div class="d-flex justify-content-end">
            <input type="submit" name="{{ $op }}" value="{{ $opType }}" class="btn btn-secondary btn-manager">
        </div>
    </form>
@endsection